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
    private BeacmcBoss plugin;
    private final ActionManager actionManager;
    private final RequirementManager requirementManager;

    public TriggerManager() {
        plugin = BeacmcBoss.getInstance();
        actionManager = BeacmcBoss.getActionManager();
        requirementManager = BeacmcBoss.getRequirementManager();
    }

    public void executeTriggers(Player player, Boss boss, TriggerType triggerType) {
        for (ConfigurationSection section : boss.getTriggers()) {
            String type = section.getString("type").toUpperCase();
            if (isTriggerType(type) && triggerType == TriggerType.valueOf(type)) {
                if(requirementManager.executeRequirements(player, boss, section.getStringList("requirements"))) {
                    actionManager.executeActions(player, boss, section.getStringList("actions"));
                }
            }
        }
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
