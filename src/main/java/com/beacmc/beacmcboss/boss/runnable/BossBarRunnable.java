package com.beacmc.beacmcboss.boss.runnable;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.config.BaseConfig;
import com.beacmc.beacmcboss.util.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class BossBarRunnable extends BukkitRunnable {

    private final Logger logger;
    private final Set<Player> players;
    private final BaseConfig config;
    private Boss boss;
    private BossBar bar;

    public BossBarRunnable(Boss boss) {
        this.logger = BeacmcBoss.getInstance().getLogger();
        this.players = new HashSet<>();
        this.boss = boss;
        this.config = BeacmcBoss.getBaseConfig();
        this.bar = Bukkit.createBossBar(Color.of(PlaceholderAPI.setPlaceholders(null, config.getBossBarTitle())), config.getBarColor(), config.getBarStyle());
        Bukkit.getOnlinePlayers().forEach(bar::addPlayer);
        players.addAll(Bukkit.getOnlinePlayers());
        runTaskTimer(BeacmcBoss.getInstance(), 0L, 20L);
    }

    @Override
    public void run() {
        if (!boss.isSpawned()) {
            this.clear();
            return;
        }


        final LivingEntity entity = boss.getLivingEntity();
        final String title = Color.of(PlaceholderAPI.setPlaceholders(null, config.getBossBarTitle()));

        if (entity == null) {
            this.clear();
            return;
        }

        bar.setTitle(title);
        bar.setProgress(entity.getHealth() / entity.getMaxHealth());

        Bukkit.getOnlinePlayers().forEach(p -> {
            if (!isPlayerInBossBar(p)) {
                players.add(p);
                bar.addPlayer(p);
            }
        });
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void clear() {
        this.cancel();
        this.players.clear();
        this.bar.removeAll();
    }

    public boolean isPlayerInBossBar(Player player) {
        return players.stream().filter(p -> p == player).findFirst().orElse(null) != null;
    }

    public BossBar getBossBar() {
        return bar;
    }
}
