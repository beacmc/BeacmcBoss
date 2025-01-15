package com.beacmc.beacmcboss.config.impl;

import com.beacmc.beacmcboss.config.ConfigValue;

public class LanguageConfig {

    @ConfigValue(key = "messages.no-permission")
    private String noPermissionMessage;

    @ConfigValue(key = "messages.reload")
    private String reloadMessage;

    @ConfigValue(key = "messages.boss-already-spawned")
    private String bossAlreadySpawnedMessage;

    @ConfigValue(key = "messages.boss-not-spawned")
    private String bossNotSpawnedMessage;

    @ConfigValue(key = "messages.boss-stopped")
    private String bossStoppedMessage;

    @ConfigValue(key = "messages.boss-spawned")
    private String bossSpawnedMessage;

    @ConfigValue(key = "messages.boss-not-found")
    private String bossNotFoundMessage;

    @ConfigValue(key = "messages.no-args")
    private String noArgsMessage;

    @ConfigValue(key = "messages.boss-tp")
    private String bossTpMessage;

    @ConfigValue(key = "messages.item-already-added")
    private String itemAlreadyAddedMessage;

    @ConfigValue(key = "messages.item-not-found")
    private String itemNotFoundMessage;

    @ConfigValue(key = "messages.item-null")
    private String itemNullMessage;

    @ConfigValue(key = "messages.item-removed")
    private String itemRemovedMessage;

    @ConfigValue(key = "messages.item-added")
    private String itemAddedMessage;

    public String getBossAlreadySpawnedMessage() {
        return bossAlreadySpawnedMessage;
    }

    public String getBossNotFoundMessage() {
        return bossNotFoundMessage;
    }

    public String getBossNotSpawnedMessage() {
        return bossNotSpawnedMessage;
    }

    public String getBossSpawnedMessage() {
        return bossSpawnedMessage;
    }

    public String getBossStoppedMessage() {
        return bossStoppedMessage;
    }

    public String getBossTpMessage() {
        return bossTpMessage;
    }

    public String getItemAddedMessage() {
        return itemAddedMessage;
    }

    public String getItemAlreadyAddedMessage() {
        return itemAlreadyAddedMessage;
    }

    public String getItemNotFoundMessage() {
        return itemNotFoundMessage;
    }

    public String getItemNullMessage() {
        return itemNullMessage;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

    public String getItemRemovedMessage() {
        return itemRemovedMessage;
    }

    public String getNoArgsMessage() {
        return noArgsMessage;
    }

    public String getReloadMessage() {
        return reloadMessage;
    }
}
