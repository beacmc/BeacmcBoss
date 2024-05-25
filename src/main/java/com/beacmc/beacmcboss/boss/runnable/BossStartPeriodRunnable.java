package com.beacmc.beacmcboss.boss.runnable;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.scheduler.BukkitRunnable;

public class BossStartPeriodRunnable extends BukkitRunnable {

    private final BeacmcBoss plugin;
    private int seconds;
    private Boss boss;

    public BossStartPeriodRunnable(Boss boss) {
        plugin = BeacmcBoss.getInstance();
        this.boss = boss;
        seconds = boss.getSpawnTimePeriod() * 60;
        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if(!boss.isSpawned()) {
            seconds -= 1;
            if (seconds <= 0) {
                seconds = boss.getSpawnTimePeriod() * 60;
                boss.spawn();
            }
        }
    }

    public int getSeconds() {
        return seconds;
    }
}
