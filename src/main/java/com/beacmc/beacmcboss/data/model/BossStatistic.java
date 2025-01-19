package com.beacmc.beacmcboss.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "boss_stats")
public class BossStatistic {

    @DatabaseField(id = true, columnName = "id", canBeNull = false)
    private String id;

    @DatabaseField(columnName = "last_killer", defaultValue = "0")
    private String lastKiller;

    public BossStatistic() {}

    public String getId() {
        return id;
    }

    public String getLastKiller() {
        return lastKiller;
    }

    public BossStatistic setLastKiller(String  lastKiller) {
        this.lastKiller = lastKiller;
        return this;
    }

    public BossStatistic setId(String id) {
        this.id = id;
        return this;
    }
}
