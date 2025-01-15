package com.beacmc.beacmcboss.boss.manager;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.runnable.BossStartPeriodRunnable;
import com.beacmc.beacmcboss.boss.runnable.TimerRunnable;
import com.beacmc.beacmcboss.config.impl.BaseConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

public class BossManager {

    private final BeacmcBoss plugin;
    private final Logger logger;
    private final Random random;
    private Map<String, Boss> registerBosses;

    public BossManager() {
        plugin = BeacmcBoss.getInstance();
        logger = plugin.getLogger();
        registerBosses = new HashMap<>();
        random = new Random();
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
            if(boss.isSpawned()) {
                boss.despawn(null);
            }
            boss.getTimerRunnable().cancel();
            boss.getBossStartRunnable().cancel();
        });
        registerBosses.clear();
    }

    public void loadBosses() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("bosses");
        section.getKeys(false).forEach(key -> {
            File file = new File(plugin.getDataFolder(), "bosses/" + section.getString(key));
            if(file.exists() && file.getName().endsWith(".yml")) {
                Boss boss = new Boss(file);
                registerBoss(key, boss);
            }
        });
    }


    public @Nullable Boss getBossByName(String name) {
        return registerBosses.get(name);
    }

    public @Nullable Boss getBossByEntity(LivingEntity entity) {
        return registerBosses.values().stream()
                .filter(boss -> boss.getLivingEntity() == entity)
                .findFirst()
                .orElse(null);
    }

    public Location createSpawnLocation(Boss boss) {
        if (!boss.isRandomSpawnLocationEnabled()) {
            return boss.getSpawnLocation();
        }
        return searchSafeLocation(boss.getRandomSpawnLocationWorld(), boss.getRandomSpawnLocationRadius()).add(0, 1, 0);
    }

    public @NotNull Location searchSafeLocation(World world, Integer radius) {
        for (int i = 0; i < 30; i++) {
            int diameter = radius * 2;
            int minX = -radius;
            int minZ = -radius;

            int x = minX + random.nextInt(diameter);
            int z = minZ + random.nextInt(diameter);

            Block block = world.getHighestBlockAt(x, z);
            if (isSafeLocation(block.getType())) {
                return block.getLocation();
            }
        }
        return world.getHighestBlockAt(0, 0).getLocation();
    }

    private boolean isSafeLocation(Material material) {
        return material != Material.WATER
                && material != Material.LAVA
                && !material.isAir();
    }

    public boolean exists(Boss boss) {
        return registerBosses.containsValue(boss);
    }

    public boolean exists(LivingEntity entity) {
        return getBossByEntity(entity) != null;
    }

    public boolean exists(String bossName) {
        return registerBosses.containsKey(bossName);
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
