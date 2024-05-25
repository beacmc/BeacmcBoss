package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.Color;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;

public class MessageAction extends Action {


    @Override
    public String getName() {
        return "[message]";
    }

    @Override
    public String getDescription() {
        return "send message to player";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        player.sendMessage(Color.of(PlaceholderAPI.setPlaceholders(player, param)));
    }
}
