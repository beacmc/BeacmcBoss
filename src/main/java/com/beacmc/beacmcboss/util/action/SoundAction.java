package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class SoundAction extends Action {

    private final Logger logger;

    public SoundAction() {
        logger = BeacmcBoss.getInstance().getLogger();
    }

    @Override
    public String getName() {
        return "[sound]";
    }

    @Override
    public String getDescription() {
        return "play sound to player";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        String[] args = param.split(";");
        try {
            if(args.length >= 3) {
                player.playSound(
                        player.getLocation(),
                        Sound.valueOf(args[0]),
                        Float.parseFloat(args[1]),
                        Float.parseFloat(args[2]));
                return;
            }
            player.playSound(player.getLocation(), Sound.valueOf(param), 1.0f, 1.0f);
        } catch (NumberFormatException | NullPointerException e) {
            logger.severe("was expected to be a number, but you set a different character.");
        } catch (IllegalArgumentException e) {
            logger.severe("Sound not found");
        }
    }
}
