package com.beacmc.beacmcboss.boss.runnable;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.config.impl.BaseConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.stream.Collectors;

public class NearbyRunnable extends BukkitRunnable {

    private final BeacmcBoss plugin;
    private BaseConfig config;
    private TriggerManager manager;
    private Boss boss;

    public NearbyRunnable(Boss boss) {
        plugin = BeacmcBoss.getInstance();
        config = BeacmcBoss.getBaseConfig();
        manager = BeacmcBoss.getTriggerManager();
        this.boss = boss;
        this.runTaskTimer(plugin, 0L, config.getNearbyExecuteTime() * 20L);
    }

    @Override
    public void run() {
        LivingEntity entity = boss.getLivingEntity();
        if (entity != null) {
            Collection<Player> list = Bukkit.getOnlinePlayers().stream()
                    .filter(player -> player.getWorld().equals(entity.getWorld()))
                    .filter(player -> player.getLocation().distance(entity.getLocation()) <= config.getNearbyRadius())
                    .collect(Collectors.toList());
            list.forEach(player -> manager.executeTriggers(player, boss, TriggerType.NEARBY_PLAYERS));
        }
    }
}
