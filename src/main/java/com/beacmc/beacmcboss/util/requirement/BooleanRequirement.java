package com.beacmc.beacmcboss.util.requirement;

import com.beacmc.beacmcboss.api.requirement.Requirement;
import com.beacmc.beacmcboss.boss.Boss;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class BooleanRequirement implements Requirement {


    @Override
    public String getName() {
        return "[boolean]";
    }

    @Override
    public String getDescription() {
        return "checks whether the message outputs yes or true";
    }

    @Override
    public boolean execute(Player player, Boss boss, String param) {
        final String msg = PlaceholderAPI.setPlaceholders(player, param);
        if(msg.isEmpty())
            return false;

        if(msg.contains("true") || msg.contains("yes")) {
            return !msg.startsWith("!");
        } else if (msg.contains("false") || msg.contains("no")) {
            return msg.startsWith("!");
        }
        return false;
    }
}
