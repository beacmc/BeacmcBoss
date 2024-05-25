package com.beacmc.beacmcboss.boss.runnable;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.config.BaseConfig;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerRunnable extends BukkitRunnable {

    private final BeacmcBoss plugin;
    private BaseConfig config;
    private TriggerManager manager;
    private Boss boss;

    public TimerRunnable(Boss boss) {
        plugin = BeacmcBoss.getInstance();
        config = BeacmcBoss.getBaseConfig();
        manager = BeacmcBoss.getTriggerManager();
        this.boss = boss;
        this.runTaskTimer(plugin, 0L, config.getTimerTime());
    }

    @Override
    public void run() {
        manager.executeTriggers(null, boss, TriggerType.TIMER);
    }
}
