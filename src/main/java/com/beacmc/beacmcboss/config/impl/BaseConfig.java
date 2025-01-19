package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.config.ConfigValue;
import com.beacmc.beacmcboss.data.DatabaseType;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public class BaseConfig {

    @ConfigValue(key = "database.type", defaultValue = "SQLITE", setDefaultValueOnNull = true)
    private DatabaseType databaseType;

    @ConfigValue(key = "database.host",  defaultValue = "localhost:3306", setDefaultValueOnNull = true)
    private String databaseHost;

    @ConfigValue(key = "database.database",  defaultValue = "beacmcboss", setDefaultValueOnNull = true)
    private String databaseName;

    @ConfigValue(key = "database.username",  defaultValue = "beacmc", setDefaultValueOnNull = true)
    private String databaseUsername;

    @ConfigValue(key = "database.password",  defaultValue = "1hduhd7hysHs7d9A", setDefaultValueOnNull = true)
    private String databasePassword;

    @ConfigValue(key = "settings.nearby-radius")
    private int nearbyRadius;

    @ConfigValue(key = "settings.timer-time")
    private int timerTime;

    @ConfigValue(key = "settings.prefix")
    private String prefix;

    @ConfigValue(key = "settings.locale-file", defaultValue = "en.yml", setDefaultValueOnNull = true)
    private File localeFile;

    @ConfigValue(key = "settings.nearby-execute-time")
    private int nearbyExecuteTime;

    @ConfigValue(key = "bosses")
    private ConfigurationSection bossesSection;

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public ConfigurationSection getBossesSection() {
        return bossesSection;
    }

    public File getLocaleFile() {
        return localeFile;
    }

    public int getNearbyExecuteTime() {
        return nearbyExecuteTime;
    }

    public int getNearbyRadius() {
        return nearbyRadius;
    }

    public int getTimerTime() {
        return timerTime;
    }

    public String getPrefix() {
        return prefix;
    }
}
