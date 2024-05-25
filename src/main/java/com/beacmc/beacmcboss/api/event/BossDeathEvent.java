package com.beacmc.beacmcboss.api.event;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BossDeathEvent extends Event implements Cancellable {

    private final Boss boss;
    private final Player killer;
    private boolean cancel;
    private static final HandlerList handlers = new HandlerList();

    public BossDeathEvent(Boss boss, Player killer) {
        this.boss = boss;
        this.killer = killer;
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

    public Player getKiller() {
        return killer;
    }
}
