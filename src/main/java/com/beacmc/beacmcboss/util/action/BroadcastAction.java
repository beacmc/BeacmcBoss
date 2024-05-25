package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastAction extends Action {

    @Override
    public String getName() {
        return "[broadcast]";
    }

    @Override
    public String getDescription() {
        return "broadcast message";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        final String message = Color.of(PlaceholderAPI.setPlaceholders(player, param));
        System.out.println(message);
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }
}
