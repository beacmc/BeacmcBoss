package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.config.ConfigValue;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public class BaseConfig {

    @ConfigValue(key = "settings.nearby-radius")
    private int nearbyRadius;

    @ConfigValue(key = "settings.timer-time")
    private int timerTime;

    @ConfigValue(key = "settings.prefix")
    private String prefix;

    @ConfigValue(key = "settings.locale-file")
    private File localeFile;

    @ConfigValue(key = "settings.nearby-execute-time")
    private int nearbyExecuteTime;

    @ConfigValue(key = "bosses")
    private ConfigurationSection bossesSection;

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
