package com.beacmc.beacmcboss.boss;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.event.BossDeathEvent;
import com.beacmc.beacmcboss.api.event.BossSpawnEvent;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.config.BossConfig;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.boss.runnable.BossStartPeriodRunnable;
import com.beacmc.beacmcboss.boss.runnable.NearbyRunnable;
import com.beacmc.beacmcboss.util.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class Boss extends BossConfig {

    private final BeacmcBoss plugin;
    private BukkitRunnable nearbyRunnable;

    private BossStartPeriodRunnable bossStartRunnable;
    private BukkitRunnable timerRunnable;
    private LivingEntity entity;
    private final TriggerManager triggerManager;
    private final Logger logger;

    public Boss(File file) {
        super(file);
        plugin = BeacmcBoss.getInstance();
        triggerManager = BeacmcBoss.getTriggerManager();
        logger = plugin.getLogger();
    }

    public boolean isSpawned() {
        return entity != null;
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
            entity.setCustomName(Color.of(getDisplayName()));
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getHealth());
            entity.setHealth(getHealth());
            setNearbyRunnable(new NearbyRunnable(this));
            triggerManager.executeTriggers(null, this, TriggerType.BOSS_SPAWN);
        } catch (ClassCastException e) {
            logger.severe("An error occurred during the boss spawn. Reason: invalid entity type");
        }
    }

    public void despawn(Player killer) {
        final YamlConfiguration data = BeacmcBoss.getDataConfig();

        if(isSpawned()) {
            BossDeathEvent event = new BossDeathEvent(this, killer);
            event.callEvent();
            if(event.isCancelled())
                return;

            try {
                if(killer != null) {
                    File file = new File(BeacmcBoss.getInstance().getDataFolder(), "data.yml");
                    data.load(file);
                    data.set("bosses." + getName() + ".last-killer", killer.getName());
                    data.save(file);
                }
            } catch (IOException | InvalidConfigurationException e) { }

            getNearbyRunnable().cancel();
            triggerManager.executeTriggers(null, this, TriggerType.BOSS_DEATH);
            entity.remove();
            entity = null;
        }
    }

    public int getTimeToStart() {
        return bossStartRunnable.getSeconds();
    }

    public int getLifetime() {
        return bossStartRunnable.getLifetime();
    }

    public String getLastKiller() {
        YamlConfiguration data = BeacmcBoss.getDataConfig();
        return data.getString("bosses." + getName() + ".last-killer");
    }

    public String getName() {
        final BossManager bossManager = BeacmcBoss.getBossManager();

        Map<String, Boss> bosses = bossManager.getRegisterBosses();
        return bosses.entrySet().stream()
                .filter(entry -> entry.getValue().equals(this))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public void setLivingEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getLivingEntity() {
        return entity;
    }

    public BukkitRunnable getNearbyRunnable() {
        return nearbyRunnable;
    }

    public BukkitRunnable getTimerRunnable() {
        return timerRunnable;
    }

    public BossStartPeriodRunnable getBossStartRunnable() {
        return bossStartRunnable;
    }

    public void setBossStartRunnable(BossStartPeriodRunnable bossStartRunnable) {
        this.bossStartRunnable = bossStartRunnable;
    }

    public void setTimerRunnable(BukkitRunnable timerRunnable) {
        this.timerRunnable = timerRunnable;
    }

    public void setNearbyRunnable(BukkitRunnable nearbyRunnable) {
        this.nearbyRunnable = nearbyRunnable;
    }
}
