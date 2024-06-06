package com.beacmc.beacmcboss.boss.runnable;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.scheduler.BukkitRunnable;

public class BossStartPeriodRunnable extends BukkitRunnable {

    private final BeacmcBoss plugin;
    private int seconds;
    private int lifetime;
    private Boss boss;

    public BossStartPeriodRunnable(Boss boss) {
        plugin = BeacmcBoss.getInstance();
        this.boss = boss;
        seconds = boss.getSpawnTimePeriod() * 60;
        lifetime = 0;
        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if(!boss.isSpawned()) {
            seconds -= 1;
            lifetime = 0;
            if (seconds <= 0) {
                seconds = boss.getSpawnTimePeriod() * 60;
                boss.spawn();
            }
        } else {
            lifetime += 1;
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public int getLifetime() {
        return lifetime;
    }
}
