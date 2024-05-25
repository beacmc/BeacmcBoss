package com.beacmc.beacmcboss.config;

import org.bukkit.configuration.ConfigurationSection;

import java.io.File;

public interface BaseConfig {

    int getNearbyRadius();

    int getTimerTime();

    String getPrefix();

    File getLocaleFile();

    int getNearbyExecuteTime();

    ConfigurationSection getBossesSection();
}
