package com.beacmc.beacmcboss.boss.manager;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.runnable.BossStartPeriodRunnable;
import com.beacmc.beacmcboss.boss.runnable.TimerRunnable;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BossManager {

    private final BeacmcBoss plugin;
    private final Logger logger;
    private Map<String, Boss> registerBosses;

    public BossManager() {
        plugin = BeacmcBoss.getInstance();
        logger = plugin.getLogger();
        registerBosses = new HashMap<>();
        loadBosses();
    }

    public void registerBoss(String name, Boss boss) {
        try {
            if (isRegisterBoss(boss) || isRegisterBoss(name)) {
                logger.severe("Boss " + name + " already registered");
                return;
            }

            if (boss.getSpawnLocation() == null) {
                logger.severe("Boss " + name + "'s spawn location is unknown.");
                return;
            }
            boss.setTimerRunnable(new TimerRunnable(boss));
            boss.setBossStartRunnable(new BossStartPeriodRunnable(boss));

            registerBosses.put(name, boss);
        } catch (IllegalArgumentException e) { }
    }

    public void unregisterBoss(String name, Boss boss) {
        if(isRegisterBoss(boss) || isRegisterBoss(name)) {
            boss.getBossStartRunnable().cancel();
            boss.getNearbyRunnable().cancel();
            boss.getTimerRunnable().cancel();
            registerBosses.remove(name, boss);
            return;
        }
        logger.severe("Boss " + name + " not registered");
    }

    public void unregisterAll() {
        registerBosses.values().forEach(boss -> {
            if(boss.isSpawned())
                boss.despawn(null);
        });
        registerBosses.clear();
    }

    private void loadBosses() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("bosses");
        section.getKeys(false).forEach(key -> {
            File file = new File(plugin.getDataFolder(), "bosses/" + section.getString(key));
            if(file.exists() && file.getName().endsWith(".yml")) {
                Boss boss = new Boss(file);
                registerBoss(key, boss);
            }
        });
    }

    @Nullable
    public Boss getBossByName(String name) {
        return registerBosses.get(name);
    }

    public Boss getBossByEntity(LivingEntity entity) {
        for (Boss boss : registerBosses.values()) {
            if (boss.getLivingEntity() == entity)
                return boss;
        }
        return null;
    }

    public boolean isRegisterBoss(String name) {
        return registerBosses.containsKey(name);
    }

    public boolean isRegisterBoss(Boss boss) {
        return registerBosses.containsValue(boss);
    }

    public Map<String, Boss> getRegisterBosses() {
        return registerBosses;
    }
}
