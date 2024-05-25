package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.config.BaseConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class BaseConfigImpl implements BaseConfig {

    private final FileConfiguration config;
    private final String prefix;
    private final int timerTime;
    private final int nearbyRadius;
    private final int nearbyExecuteTime;
    private final ConfigurationSection bossesSection;
    private final File localeFile;

    public BaseConfigImpl() {
        config = BeacmcBoss.getInstance().getConfig();
        ConfigurationSection settings = config.getConfigurationSection("settings");
        prefix = settings.getString("prefix");
        timerTime = settings.getInt("timer-time");
        nearbyRadius = settings.getInt("nearby-radius");
        nearbyExecuteTime = settings.getInt("nearby-execute-time");
        bossesSection = config.getConfigurationSection("bosses");
        localeFile = new File(BeacmcBoss.getInstance().getDataFolder().getAbsoluteFile() + "/lang/" + settings.getString("locale-file"));
    }

    public String getPrefix() {
        return prefix;
    }

    public int getNearbyExecuteTime() {
        return nearbyExecuteTime;
    }

    public File getLocaleFile() {
        return localeFile;
    }

    public ConfigurationSection getBossesSection() {
        return bossesSection;
    }

    public int getNearbyRadius() {
        return nearbyRadius;
    }

    public int getTimerTime() {
        return timerTime;
    }
}
