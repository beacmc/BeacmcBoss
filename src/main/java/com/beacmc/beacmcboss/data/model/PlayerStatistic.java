package com.beacmc.beacmcboss.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

@DatabaseTable(tableName = "player_stats")
public class PlayerStatistic {

    @DatabaseField(id = true, columnName = "name", canBeNull = false)
    private UUID uuid;

    @DatabaseField(columnName = "boss_kills", defaultValue = "0")
    private Integer bossKills;

    @DatabaseField(columnName = "total_damage", defaultValue = "0")
    private long totalDamage;

    public PlayerStatistic() {}

    public UUID getUuid() {
        return uuid;
    }

    public Integer getBossKills() {
        return bossKills;
    }

    public long getTotalDamage() {
        return totalDamage;
    }

    public PlayerStatistic setBossKills(Integer bossKills) {
        this.bossKills = bossKills;
        return this;
    }

    public PlayerStatistic setTotalDamage(long totalDamage) {
        this.totalDamage = totalDamage;
        return this;
    }

    public PlayerStatistic setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
}
