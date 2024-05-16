package com.beacmc.beacmcboss.api.trigger;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Trigger {

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute(Player player, Boss boss, List<String> actions, List<String> requirements);
}
