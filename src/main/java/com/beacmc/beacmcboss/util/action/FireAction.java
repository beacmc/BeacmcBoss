package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public class FireAction extends Action {


    @Override
    public String getName() {
        return "[set_fire_ticks]";
    }

    @Override
    public String getDescription() {
        return "Sets the player on fire for a certain duration in ticks";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        if(player == null)
            return;

        int fireTicks = 20;
        try {
            fireTicks = Integer.parseInt(param);
        } catch (NumberFormatException e) { }

        player.setFireTicks(fireTicks);
    }
}
