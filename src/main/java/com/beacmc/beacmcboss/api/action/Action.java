package com.beacmc.beacmcboss.api.action;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public abstract class Action {

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute(Player player, Boss boss, String param);
}
