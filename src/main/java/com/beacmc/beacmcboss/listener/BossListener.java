package com.beacmc.beacmcboss.listener;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.event.BossDeathEvent;
import com.beacmc.beacmcboss.api.event.DamageBossByPlayerEvent;
import com.beacmc.beacmcboss.api.event.DamagePlayerByBossEvent;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.data.DataManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.PluginManager;

public class BossListener implements Listener {

    private final BossManager bossManager;
    private final TriggerManager triggerManager;
    private final DataManager dataManager;

    public BossListener() {
        bossManager = BeacmcBoss.getBossManager();
        dataManager = BeacmcBoss.getDataManager();
        triggerManager = BeacmcBoss.getTriggerManager();
    }

    @EventHandler
    public void onBossDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Boss boss = bossManager.getBossByEntity(entity);
        final Player killer = entity.getKiller();

        if (bossManager.exists(boss)) {

            BossDeathEvent bossDeathEvent = new BossDeathEvent(boss, killer);
            BeacmcBoss.getInstance().getServer().getPluginManager().callEvent(bossDeathEvent);

            if (bossDeathEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            if (killer != null) {
                dataManager.setLastKiller(boss.getName(), killer.getName());
                dataManager.addBossKill(killer.getUniqueId());
            }

            event.getDrops().clear();
            event.setDroppedExp(0);

            triggerManager.executeTriggers(killer, boss, TriggerType.BOSS_DEATH);
            boss.setLivingEntity(null);
        }
    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof LivingEntity))
            return;

        final LivingEntity entity = (LivingEntity) event.getDamager();
        final Boss boss = bossManager.getBossByEntity(entity);

        if (bossManager.exists(boss)) {
            event.setDamage(boss.getCustomDamage());
        }
    }

    @EventHandler
    public void damageTriggers(EntityDamageByEntityEvent event) {
        final PluginManager manager = BeacmcBoss.getInstance().getServer().getPluginManager();

        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            final LivingEntity entity = (LivingEntity) event.getDamager();
            final Boss boss = bossManager.getBossByEntity(entity);

            if (bossManager.exists(boss)) {
                DamagePlayerByBossEvent damagePlayerByBossEvent = new DamagePlayerByBossEvent(boss, player);
                manager.callEvent(damagePlayerByBossEvent);

                if (damagePlayerByBossEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }

                triggerManager.executeTriggers(player, boss, TriggerType.DAMAGE_BY_BOSS);
            }
        }

        else if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            final Player player = (Player) event.getDamager();
            final LivingEntity entity = (LivingEntity) event.getEntity();
            final Boss boss = bossManager.getBossByEntity(entity);

            if (bossManager.exists(boss)) {
                DamageBossByPlayerEvent damageBossByPlayerEvent = new DamageBossByPlayerEvent(boss, player);
                manager.callEvent(damageBossByPlayerEvent);

                if (damageBossByPlayerEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }

                boss.addDamageQueue(player.getUniqueId(), (long) event.getDamage());
                triggerManager.executeTriggers(player, boss, TriggerType.DAMAGE_BY_PLAYER);
            }
        }
    }
}
