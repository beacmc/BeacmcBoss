package com.beacmc.beacmcboss.listener;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.data.DataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final DataManager dataManager;

    public PlayerListener() {
        dataManager = BeacmcBoss.getDataManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        dataManager.createPlayerStatistic(event.getPlayer().getUniqueId());
    }
}
