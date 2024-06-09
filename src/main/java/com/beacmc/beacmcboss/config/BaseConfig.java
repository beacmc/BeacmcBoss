package com.beacmc.beacmcboss.config;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public interface BaseConfig {

    int getNearbyRadius();

    boolean isBossBarEnable();

    String getBossBarTitle();

    BarStyle getBarStyle();

    BarColor getBarColor();

    int getTimerTime();

    String getPrefix();

    File getLocaleFile();

    int getNearbyExecuteTime();

    ConfigurationSection getBossesSection();
}
