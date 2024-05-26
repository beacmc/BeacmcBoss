package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public class DamageAction extends Action {

    @Override
    public String getName() {
        return "[damage]";
    }

    @Override
    public String getDescription() {
        return "cause damage to the player";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        if (player == null)
            return;

        try {
            player.damage(Double.parseDouble(param));
        } catch (NumberFormatException e) { }
    }
}
