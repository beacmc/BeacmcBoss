package com.beacmc.beacmcboss.api.action;

import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public interface Action {

    String getName();

    String getDescription();

    void execute(Player player, Boss boss, String param);
}
