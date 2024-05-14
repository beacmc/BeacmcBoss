package com.beacmc.beacmcboss.boss;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.config.BossConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;

public class Boss extends BossConfig {

    private final BeacmcBoss plugin;
    private LivingEntity entity;

    public Boss(File file) {
        super(YamlConfiguration.loadConfiguration(file));
        plugin = BeacmcBoss.getInstance();
    }

    public boolean isSpawned() {
        return entity != null || !entity.isDead();
    }

    public double getCurrentHealth() {
        return entity.getHealth();
    }

    public void setCurrentHealth(double health) {
        entity.setHealth(health);
    }

    public void setLivingEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getLivingEntity() {
        return entity;
    }
}
