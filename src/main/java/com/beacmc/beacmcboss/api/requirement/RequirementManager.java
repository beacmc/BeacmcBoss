package com.beacmc.beacmcboss.api.requirement;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequirementManager {

    private Set<Requirement> registerRequirements;
    private BeacmcBoss plugin;

    public RequirementManager() {
        plugin = BeacmcBoss.getInstance();
        registerRequirements = new HashSet<>();
    }

    public void registerAction(Requirement requirement) {
        if(isRegisterAction(requirement)) {
            plugin.getLogger().severe("requirement " + requirement.getName() + " already registered");
            return;
        }
        plugin.getLogger().info("register requirement " + requirement.getName() + " - " + requirement.getDescription());
        registerRequirements.add(requirement);
    }

    public void registerActions(Requirement... requirements) {
        for(Requirement requirement : requirements) {
            if (isRegisterAction(requirement)) {
                plugin.getLogger().severe("requirement " + requirement.getName() + " already registered");
                continue;
            }
            plugin.getLogger().info("register requirement " + requirement.getName() + " - " + requirement.getDescription());
            registerRequirements.add(requirement);
        }
    }

    public void unregisterAction(Requirement requirement) {
        if(!isRegisterAction(requirement)) {
            plugin.getLogger().severe("requirement " + requirement.getName() + " not registered");
            return;
        }
        plugin.getLogger().info("unregister requirement " + requirement.getName() + " - " + requirement.getDescription());
        registerRequirements.remove(requirement);
    }

    public boolean isRegisterAction(Requirement requirement) {
        String name = requirement.getName();
        for (Requirement execute : registerRequirements) {
            if(name.equals(execute.getName()))
                return true;
        }
        return false;
    }

    public boolean executeRequirements(Player player, Boss boss, List<String> requirements) {
        for (String execute : requirements) {
            for (Requirement req : registerRequirements) {
                String name = req.getName();
                if (!execute.startsWith(name))
                    continue;

                if(!req.execute(player, boss, execute.replace(name, "").trim()))
                    return false;
            }
        }
        return true;
    }

    public Set<Requirement> getRegisterRequirements() {
        return registerRequirements;
    }
}
