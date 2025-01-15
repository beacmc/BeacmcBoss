package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConsoleAction implements Action {


    @Override
    public String getName() {
        return "[console]";
    }

    @Override
    public String getDescription() {
        return "dispatch console command";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(player, param));
    }
}
