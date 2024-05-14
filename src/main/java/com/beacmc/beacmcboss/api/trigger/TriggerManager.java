package com.beacmc.beacmcboss.api.trigger;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TriggerManager {
    private Set<Trigger> registerTriggers;
    private BeacmcBoss plugin;

    public TriggerManager() {
        plugin = BeacmcBoss.getInstance();
        registerTriggers = new HashSet<>();
    }

    public void registerAction(Trigger trigger) {
        if(isRegisterAction(trigger)) {
            plugin.getLogger().severe("trigger " + trigger.getName() + " already registered");
            return;
        }
        plugin.getLogger().info("register trigger " + trigger.getName() + " - " + trigger.getDescription());
        registerTriggers.add(trigger);
    }

    public void registerActions(Trigger... triggers) {
        for(Trigger trigger : triggers) {
            if (isRegisterAction(trigger)) {
                plugin.getLogger().severe("trigger " + trigger.getName() + " already registered");
                continue;
            }
            plugin.getLogger().info("register trigger " + trigger.getName() + " - " + trigger.getDescription());
            registerTriggers.add(trigger);
        }
    }

    public void unregisterAction(Trigger trigger) {
        if(!isRegisterAction(trigger)) {
            plugin.getLogger().severe("trigger " + trigger.getName() + " not registered");
            return;
        }
        plugin.getLogger().info("unregister trigger " + trigger.getName() + " - " + trigger.getDescription());
        registerTriggers.remove(trigger);
    }

    public boolean isRegisterAction(Trigger trigger) {
        String name = trigger.getName();
        for (Trigger execute : registerTriggers) {
            if(name.equals(execute.getName()))
                return true;
        }
        return false;
    }

    public void executeTriggers(Player player, Boss boss) {
        
    }

    public Set<Trigger> getRegisterTriggers() {
        return registerTriggers;
    }

}
