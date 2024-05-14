package com.beacmc.beacmcboss.api.requirement;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public abstract class Requirement {

    public abstract String getName();

    public abstract String getDescription();

    public abstract boolean execute(Player player, Boss boss, String param);

}
