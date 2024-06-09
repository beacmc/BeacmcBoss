package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.config.BaseConfig;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
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
    private final boolean bossBarEnable;
    private final String bossBarTitle;
    private BarStyle barStyle;
    private BarColor barColor;

    public BaseConfigImpl() {
        config = BeacmcBoss.getInstance().getConfig();
        ConfigurationSection settings = config.getConfigurationSection("settings");
        ConfigurationSection bossbarSection = settings.getConfigurationSection("boss-bar");
        prefix = settings.getString("prefix");
        timerTime = settings.getInt("timer-time");
        nearbyRadius = settings.getInt("nearby-radius");
        nearbyExecuteTime = settings.getInt("nearby-execute-time");
        bossesSection = config.getConfigurationSection("bosses");
        localeFile = new File(BeacmcBoss.getInstance().getDataFolder().getAbsoluteFile() + "/lang/" + settings.getString("locale-file"));
        bossBarEnable = bossbarSection.getBoolean("enable");
        bossBarTitle = bossbarSection.getString("text");
        try {
            barStyle = BarStyle.valueOf(bossbarSection.getString("bar-style"));
            barColor = BarColor.valueOf(bossbarSection.getString("bar-color"));
        } catch (IllegalArgumentException e) { }
    }

    public String getPrefix() {
        return prefix;
    }

    public int getNearbyExecuteTime() {
        return nearbyExecuteTime;
    }

    public BarColor getBarColor() {
        return barColor;
    }

    public BarStyle getBarStyle() {
        return barStyle;
    }

    public boolean isBossBarEnable() {
        return bossBarEnable;
    }

    public String getBossBarTitle() {
        return bossBarTitle;
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
