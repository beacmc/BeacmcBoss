package com.beacmc.beacmcboss;

import org.bukkit.plugin.java.JavaPlugin;

public final class BeacmcBoss extends JavaPlugin {

    private static BeacmcBoss instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static BeacmcBoss getInstance() {
        return instance;
    }
}
