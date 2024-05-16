package com.beacmc.beacmcboss;

import com.beacmc.beacmcboss.api.action.ActionManager;
import com.beacmc.beacmcboss.api.requirement.RequirementManager;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeacmcBoss extends JavaPlugin {

    private static BeacmcBoss instance;
    private static ActionManager actionManager;
    private static RequirementManager requirementManager;
    private static TriggerManager triggerManager;

    @Override
    public void onEnable() {
        actionManager = new ActionManager();
        requirementManager = new RequirementManager();
        triggerManager = new TriggerManager();
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ActionManager getActionManager() {
        return actionManager;
    }

    public static RequirementManager getRequirementManager() {
        return requirementManager;
    }

    public static TriggerManager getTriggerManager() {
        return triggerManager;
    }

    public static BeacmcBoss getInstance() {
        return instance;
    }
}
