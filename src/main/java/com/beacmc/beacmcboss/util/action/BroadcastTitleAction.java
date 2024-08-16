package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastTitleAction extends Action {

    @Override
    public String getName() {
        return "[broadcast_title]";
    }

    @Override
    public String getDescription() {
        return "send title to all players";
    }

    @Override
    public void execute(Player ignored, Boss boss, String param) {
        final String message = Color.of(PlaceholderAPI.setPlaceholders(ignored, param));
        final String[] args = message.split(";");

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (args.length == 0) {
                player.sendTitle(message, "", 10, 20, 10);
            } else if (args.length >= 2) {
                player.sendTitle(args[0], args[1], 10, 20, 10);
            }
        });
    }
}
