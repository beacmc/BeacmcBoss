package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class BroadcastSoundAction extends Action {

    private final Logger logger;

    public BroadcastSoundAction() {
        logger = BeacmcBoss.getInstance().getLogger();
    }

    @Override
    public String getName() {
        return "[broadcast_sound]";
    }

    @Override
    public String getDescription() {
        return "play sound to all players";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        String[] args = param.split(";");
        try {
            if(args.length >= 3) {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    p.playSound(
                            p.getLocation(),
                            Sound.valueOf(args[0]),
                            Float.parseFloat(args[1]),
                            Float.parseFloat(args[2]));
                });
                return;
            }
            Bukkit.getOnlinePlayers().forEach(p ->
                p.playSound(
                        p.getLocation(), Sound.valueOf(param), 1.0f, 1.0f)
            );
        } catch (IllegalArgumentException e) {
            logger.severe("Sound not found");
        }
    }
}
