package com.beacmc.beacmcboss.api.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActionManager {

    private Set<Action> registerActions;
    private BeacmcBoss plugin;

    public ActionManager() {
        plugin = BeacmcBoss.getInstance();
        registerActions = new HashSet<>();
    }

    public void registerAction(Action action) {
        if(isRegisterAction(action)) {
            plugin.getLogger().severe("action " + action.getName() + " already registered");
            return;
        }
        plugin.getLogger().info("register action " + action.getName() + " - " + action.getDescription());
        registerActions.add(action);
    }

    public void registerActions(Action... actions) {
        for(Action action : actions) {
            if (isRegisterAction(action)) {
                plugin.getLogger().severe("action " + action.getName() + " already registered");
                continue;
            }
            plugin.getLogger().info("register action " + action.getName() + " - " + action.getDescription());
            registerActions.add(action);
        }
    }

    public void unregisterAction(Action action) {
        if(!isRegisterAction(action)) {
            plugin.getLogger().severe("action " + action.getName() + " not registered");
            return;
        }
        plugin.getLogger().info("unregister action " + action.getName() + " - " + action.getDescription());
        registerActions.remove(action);
    }

    public boolean isRegisterAction(Action action) {
        String name = action.getName();
        for (Action execute : registerActions) {
            if(name.equals(execute.getName()))
                return true;
        }
        return false;
    }

    public void executeActions(Player player, Boss boss, List<String> actions) {
        for (String execute : actions) {
            for (Action action : registerActions) {
                String name = action.getName();
                if (!execute.startsWith(name))
                    continue;

                action.execute(player, boss, execute.replace(name, "").trim());
            }
        }
    }

    public Set<Action> getRegisterActions() {
        return registerActions;
    }
}
