package com.beacmc.beacmcboss.api.event;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BossSpawnEvent extends Event implements Cancellable {

    private final Boss boss;
    private boolean cancel;
    private static final HandlerList handlers = new HandlerList();

    public BossSpawnEvent(Boss boss) {
        this.boss = boss;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }


    public static HandlerList getHandlerList() {
        return null;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public Boss getBoss() {
        return boss;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }
}
