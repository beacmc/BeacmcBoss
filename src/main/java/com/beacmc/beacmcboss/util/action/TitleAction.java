package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class TitleAction extends Action {

    @Override
    public String getName() {
        return "[title]";
    }

    @Override
    public String getDescription() {
        return "send title to player";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {

        final String message = PlaceholderAPI.setPlaceholders(player, Color.of(param));
        final String[] args = message.split(";");

        if(args.length == 0) {
            player.sendTitle(message, "", 10, 20, 10);
        } else if (args.length >= 2) {
            player.sendTitle(args[0], args[1], 10, 20, 10);
        }
    }
}
