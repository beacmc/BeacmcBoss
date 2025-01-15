package com.beacmc.beacmcboss.api.requirement;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public interface Requirement {

    String getName();

    String getDescription();

    boolean execute(Player player, Boss boss, String param);
}
