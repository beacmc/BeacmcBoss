package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.Color;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class ActionbarAction extends Action {

    @Override
    public String getName() {
        return "[actionbar]";
    }

    @Override
    public String getDescription() {
        return "send action bar to player";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        player.sendActionBar(Color.of(PlaceholderAPI.setPlaceholders(player, param)));
    }
}
