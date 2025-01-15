package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

public class BossStopAction implements Action {

    @Override
    public String getName() {
        return "[boss_stop]";
    }

    @Override
    public String getDescription() {
        return "will force a boss to stop";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        boss.despawn(player);
    }
}
