package com.beacmc.beacmcboss.api.trigger;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.ActionManager;
import com.beacmc.beacmcboss.api.requirement.RequirementManager;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TriggerManager {
    private Set<Trigger> registerTriggers;
    private BeacmcBoss plugin;
    private final ActionManager actionManager;
    private final RequirementManager requirementManager;

    public TriggerManager() {
        plugin = BeacmcBoss.getInstance();
        registerTriggers = new HashSet<>();
        actionManager = BeacmcBoss.getActionManager();
        requirementManager = BeacmcBoss.getRequirementManager();
    }

    public void registerTrigger(Trigger trigger) {
        if(isRegisterTrigger(trigger)) {
            plugin.getLogger().severe("trigger " + trigger.getName() + " already registered");
            return;
        }
        plugin.getLogger().info("register trigger " + trigger.getName() + " - " + trigger.getDescription());
        registerTriggers.add(trigger);
    }

    public void registerTriggers(Trigger... triggers) {
        for(Trigger trigger : triggers) {
            if (isRegisterTrigger(trigger)) {
                plugin.getLogger().severe("trigger " + trigger.getName() + " already registered");
                continue;
            }
            plugin.getLogger().info("register trigger " + trigger.getName() + " - " + trigger.getDescription());
            registerTriggers.add(trigger);
        }
    }

    public void unregisterTrigger(Trigger trigger) {
        if(!isRegisterTrigger(trigger)) {
            plugin.getLogger().severe("trigger " + trigger.getName() + " not registered");
            return;
        }
        plugin.getLogger().info("unregister trigger " + trigger.getName() + " - " + trigger.getDescription());
        registerTriggers.remove(trigger);
    }

    public boolean isRegisterTrigger(Trigger trigger) {
        String name = trigger.getName();
        for (Trigger execute : registerTriggers) {
            if(name.equals(execute.getName()))
                return true;
        }
        return false;
    }

    public void executeTriggers(Player player, Boss boss, TriggerType triggerType) {
        for (ConfigurationSection section : boss.getTriggers()) {
            String type = section.getString("type").toUpperCase();
            if (isTriggerType(type) && triggerType == TriggerType.valueOf(type)) {
                if(requirementManager.executeRequirements(player, boss, section.getStringList("requirements"))) {
                    actionManager.executeActions(player, boss, section.getStringList("actions"));
                }
                continue;
            }
            Bukkit.getLogger().severe("Invalid trigger type: " + type);
        }
    }

    public Set<Trigger> getRegisterTriggers() {
        return registerTriggers;
    }

    private boolean isTriggerType(String type) {
        try {
            TriggerType triggerType = TriggerType.valueOf(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
     }
}
