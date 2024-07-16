package com.beacmc.beacmcboss.api.event;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DamageBossByPlayerEvent extends Event implements Cancellable {

    private final Boss boss;
    private final Player player;
    private boolean cancel;
    private static final HandlerList handlers = new HandlerList();

    public DamageBossByPlayerEvent(Boss boss, Player player) {
        this.boss = boss;
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    public Boss getBoss() {
        return boss;
    }

    public Player getPlayer() {
        return player;
    }
}
