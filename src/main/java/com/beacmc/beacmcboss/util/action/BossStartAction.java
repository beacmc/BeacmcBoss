package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public class BossStartAction extends Action {

    @Override
    public String getName() {
        return "[boss_start]";
    }

    @Override
    public String getDescription() {
        return "will force the boss to spawn.";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        boss.spawn();
    }
}
