package com.beacmc.beacmcboss.listener;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.event.PlayerInteractBossEvent;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.api.trigger.TriggerType;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.data.DataManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerListener implements Listener {

    private final DataManager dataManager;
    private final BossManager bossManager;
    private final TriggerManager triggerManager;

    public PlayerListener() {
        dataManager = BeacmcBoss.getDataManager();
        triggerManager = BeacmcBoss.getTriggerManager();
        bossManager = BeacmcBoss.getBossManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        dataManager.createPlayerStatistic(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof LivingEntity entity))
            return;

        final Player player = event.getPlayer();
        final Boss boss = bossManager.getBossByEntity(entity);

        if (bossManager.exists(boss)) {
            PlayerInteractBossEvent playerInteractBossEvent = new PlayerInteractBossEvent(boss, player);
            BeacmcBoss.getInstance().getServer().getPluginManager().callEvent(playerInteractBossEvent);

            if (playerInteractBossEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            triggerManager.executeTriggers(player, boss, TriggerType.PLAYER_INTERACT_BOSS);
        }
    }
}
