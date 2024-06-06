package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class DamageAction extends Action {

    private final Logger logger;

    public DamageAction() {
        this.logger = BeacmcBoss.getInstance().getLogger();
    }

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
        } catch (NumberFormatException e) {
            logger.severe("A number in action was expected but you didn't specify it");
        }
    }
}
