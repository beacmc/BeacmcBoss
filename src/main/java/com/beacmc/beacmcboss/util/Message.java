package com.beacmc.beacmcboss.util;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.config.BaseConfig;

public class Message {

    private static BaseConfig config;

    public static String of(String message) {
        config = BeacmcBoss.getBaseConfig();
        return Color.of(message
                .replace("{PREFIX}", config.getPrefix()));
    }
}
