package com.beacmc.beacmcboss.listener;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.event.BossDeathEvent;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.io.File;
import java.io.IOException;

public class BossListener implements Listener {

    private final BossManager bossManager;
    private final TriggerManager triggerManager;

    public BossListener() {
        bossManager = BeacmcBoss.getBossManager();
        triggerManager = BeacmcBoss.getTriggerManager();
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final YamlConfiguration data = BeacmcBoss.getDataConfig();
        final Boss boss = bossManager.getBossByEntity(entity);
        final Player killer = entity.getKiller();

        if(bossManager.exists(boss)) {
            BossDeathEvent bossDeathEvent = new BossDeathEvent(boss, killer);
            bossDeathEvent.callEvent();

            if(bossDeathEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            if (killer != null) {
                try {
                    File file = new File(BeacmcBoss.getInstance().getDataFolder(), "data.yml");
                    data.load(file);
                    data.set("bosses." + boss.getName() + ".last-killer", killer.getName());
                    data.save(file);
                } catch (IOException | InvalidConfigurationException e) { }
            }

            event.getDrops().clear();
            event.setDroppedExp(0);

            triggerManager.executeTriggers(killer, boss, TriggerType.BOSS_DEATH);
            boss.setLivingEntity(null);
        }
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof LivingEntity))
            return;

        final LivingEntity entity = (LivingEntity) event.getDamager();
        final Boss boss = bossManager.getBossByEntity(entity);

        if(bossManager.exists(boss)) {
            event.setDamage(boss.getCustomDamage());
        }
    }
}
