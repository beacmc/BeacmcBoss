package com.beacmc.beacmcboss.boss;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.event.BossDeathEvent;
import com.beacmc.beacmcboss.api.event.BossSpawnEvent;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.config.BossConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.logging.Logger;

public class Boss extends BossConfig {

    private final BeacmcBoss plugin;
    private LivingEntity entity;
    private final TriggerManager triggerManager;
    private final Logger logger;

    public Boss(File file) {
        super(YamlConfiguration.loadConfiguration(file));
        plugin = BeacmcBoss.getInstance();
        triggerManager = BeacmcBoss.getTriggerManager();
        logger = plugin.getLogger();
    }

    public boolean isSpawned() {
        return entity != null || !entity.isDead();
    }

    public void spawn() {
        Location location = getSpawnLocation();
        World world = location.getWorld();
        try {
            BossSpawnEvent event = new BossSpawnEvent(this);
            event.callEvent();
            if(event.isCancelled())
                return;

            entity = (LivingEntity) world.spawnEntity(location, getEntityType());
            entity.setCustomNameVisible(true);
            entity.setCustomName(getDisplayName());
            entity.setHealth(getHealth());

        } catch (ClassCastException e) {
            logger.severe("An error occurred during the boss spawn. Reason: invalid entity type");
        }
    }

    public void despawn(Player killer) {
        if(isSpawned()) {
            BossDeathEvent event = new BossDeathEvent(this, killer);
            event.callEvent();
            if(event.isCancelled())
                return;

            entity.remove();
            entity = null;
        }
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
