package com.beacmc.beacmcboss.data;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.data.dao.DefaultDao;
import com.beacmc.beacmcboss.data.dao.impl.BossStatisticDaoImpl;
import com.beacmc.beacmcboss.data.model.BossStatistic;
import com.beacmc.beacmcboss.data.model.PlayerStatistic;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class DataManager {

    private final DefaultDao<BossStatistic, String> bossStatisticDao;
    private final DefaultDao<PlayerStatistic, String> playerStatisticDao;
    private final List<BossStatistic> bossStatistics;
    private final List<PlayerStatistic> playerStatistics;

    public DataManager(DatabaseConnector database) {
        this.bossStatisticDao = database.getBossStatisticDao();
        this.playerStatisticDao = database.getPlayerStatisticDao();
        this.bossStatistics = new LinkedList<>();
        this.playerStatistics = new LinkedList<>();
        bossStatisticDao.queryForAllAsync().thenAccept(bossStatistics::addAll);
        playerStatisticDao.queryForAllAsync().thenAccept(playerStatistics::addAll);
    }

    public void createPlayerStatistic(@NotNull UUID uuid) {
        if (getPlayerStatistic(uuid) != null)
            return;

        PlayerStatistic playerStatistic = new PlayerStatistic().setUUID(uuid)
                .setBossKills(0)
                .setTotalDamage(0L);

        playerStatisticDao.createAsync(playerStatistic);
        playerStatistics.add(playerStatistic);
    }

    public void createBossStatistic(@NotNull String bossId) {
        if (getBossStatistic(bossId) != null)
            return;

        BossStatistic bossStatistic = new BossStatistic().setId(bossId)
                .setLastKiller(null);

        bossStatisticDao.createAsync(bossStatistic);
        bossStatistics.add(bossStatistic);
    }

    public void setLastKiller(String bossId, String player) {
        BossStatistic bossStatistic = getBossStatistic(bossId);
        if (bossStatistic == null) return;

        bossStatistics.remove(bossStatistic);
        bossStatisticDao.createOrUpdateAsync(bossStatistic.setLastKiller(player));
        bossStatistics.add(bossStatistic);
    }

    public String getLastKiller(String bossId) {
        BossStatistic bossStatistic = getBossStatistic(bossId);
        return bossStatistic != null ? bossStatistic.getLastKiller() : null;
    }

    public void addBossKill(UUID uuid) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        if (playerStatistic == null) return;

        setBossKills(uuid, playerStatistic.getBossKills() + 1);
    }

    public void setBossKills(UUID uuid, Integer bossKills) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        if (playerStatistic == null) return;

        playerStatistics.remove(playerStatistic);
        playerStatisticDao.createOrUpdateAsync(playerStatistic.setBossKills(bossKills));
        playerStatistics.add(playerStatistic);
    }

    public Integer getBossKills(UUID uuid) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        return playerStatistic != null ? playerStatistic.getBossKills() : 0;
    }

    public void addTotalDamage(UUID uuid, long damage) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        if (playerStatistic == null) return;

        setTotalDamage(uuid, damage + playerStatistic.getTotalDamage());
    }

    public void setTotalDamage(UUID uuid, long damage) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        if (playerStatistic == null) return;

        playerStatistics.remove(playerStatistic);
        playerStatisticDao.createOrUpdateAsync(playerStatistic.setTotalDamage(damage));
        playerStatistics.add(playerStatistic);
    }

    public long getTotalDamage(UUID uuid) {
        PlayerStatistic playerStatistic = getPlayerStatistic(uuid);
        return playerStatistic != null ? playerStatistic.getTotalDamage() : 0L;
    }

    public BossStatistic getBossStatistic(String id) {
        return bossStatistics.stream()
                .filter(bossStatistic -> bossStatistic.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public PlayerStatistic getPlayerStatistic(UUID uuid) {
        return playerStatistics.stream()
                .filter(playerStats -> playerStats.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public List<BossStatistic> getBossStatistics() {
        return bossStatistics;
    }

    public List<PlayerStatistic> getPlayerStatistics() {
        return playerStatistics;
    }
}
