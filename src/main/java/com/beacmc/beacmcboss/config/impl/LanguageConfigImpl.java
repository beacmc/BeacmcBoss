package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.config.LanguageConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class LanguageConfigImpl implements LanguageConfig {

    private final FileConfiguration config;
    private final String noPermissionMessage;
    private final String bossSpawnedMessage;
    private final String bossAlreadySpawnedMessage;
    private final String bossNotSpawnedMessage;
    private final String bossStoppedMessage;
    private final String bossNotFoundMessage;
    private final String noArgsMessage;
    private final String bossTpMessage;

    public LanguageConfigImpl() {
        config = BeacmcBoss.getLocaleConfig();
        ConfigurationSection messages = config.getConfigurationSection("messages");
        noPermissionMessage = messages.getString("no-permission");
        bossSpawnedMessage = messages.getString("boss-spawned");
        bossAlreadySpawnedMessage = messages.getString("boss-already-spawned");
        bossNotSpawnedMessage = messages.getString("boss-not-spawned");
        bossStoppedMessage = messages.getString("boss-stopped");
        bossNotFoundMessage = messages.getString("boss-not-found");
        noArgsMessage = messages.getString("no-args");
        bossTpMessage = messages.getString("boss-tp");
    }

    public String getBossTpMessage() {
        return bossTpMessage;
    }

    public String getNoArgsMessage() {
        return noArgsMessage;
    }

    public String getBossNotFoundMessage() {
        return bossNotFoundMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getBossAlreadySpawnedMessage() {
        return bossAlreadySpawnedMessage;
    }

    public String getBossNotSpawnedMessage() {
        return bossNotSpawnedMessage;
    }

    public String getBossStoppedMessage() {
        return bossStoppedMessage;
    }

    public String getBossSpawnedMessage() {
        return bossSpawnedMessage;
    }
}
