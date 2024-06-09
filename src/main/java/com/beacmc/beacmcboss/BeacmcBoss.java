package com.beacmc.beacmcboss;

import com.beacmc.beacmcboss.api.action.ActionManager;
import com.beacmc.beacmcboss.api.requirement.RequirementManager;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.command.BossCommand;
import com.beacmc.beacmcboss.config.BaseConfig;
import com.beacmc.beacmcboss.config.LanguageConfig;
import com.beacmc.beacmcboss.config.impl.BaseConfigImpl;
import com.beacmc.beacmcboss.config.impl.LanguageConfigImpl;
import com.beacmc.beacmcboss.hook.papi.Expansion;
import com.beacmc.beacmcboss.listener.BossListener;
import com.beacmc.beacmcboss.util.action.*;
import com.beacmc.beacmcboss.util.requirement.BooleanRequirement;
import com.beacmc.beacmcboss.util.requirement.ConditionRequirement;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BeacmcBoss extends JavaPlugin {

    private static BeacmcBoss instance;
    private static ActionManager actionManager;
    private static RequirementManager requirementManager;
    private static TriggerManager triggerManager;
    private static BossManager bossManager;
    private static LanguageConfig languageConfig;
    private static BaseConfig baseConfig;
    private static YamlConfiguration dataConfig;
    private static Expansion expansion;
    private static YamlConfiguration localeConfig;

    @Override
    public void onEnable() {
        instance = this;
        createConfigs();
        dataConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "data.yml"));
        baseConfig = new BaseConfigImpl();
        localeConfig = YamlConfiguration.loadConfiguration(baseConfig.getLocaleFile());
        languageConfig = new LanguageConfigImpl();

        actionManager = new ActionManager();
        requirementManager = new RequirementManager();
        triggerManager = new TriggerManager();

        bossManager = new BossManager();

        actionManager.registerActions(new BossStartAction(), new BossStopAction(), new BossEquipAction(), new BroadcastSoundAction(), new DamageAction(), new FireAction(), new SummonAction(), new ActionbarAction(), new TitleAction(), new BroadcastAction(), new SoundAction(), new ConsoleAction(), new MessageAction());
        requirementManager.registerRequirements(new BooleanRequirement(), new ConditionRequirement());


        expansion = new Expansion();
        expansion.register();
        this.getServer().getPluginManager().registerEvents(new BossListener(), this);
        this.getCommand("boss").setExecutor(new BossCommand());
    }

    @Override
    public void onDisable() {
        bossManager.unregisterAll();
        expansion.unregister();
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

    public static BossManager getBossManager() {
        return bossManager;
    }

    public static BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public static LanguageConfig getLanguageConfig() {
        return languageConfig;
    }

    public static BeacmcBoss getInstance() {
        return instance;
    }


    public static YamlConfiguration getLocaleConfig() {
        return localeConfig;
    }

    public static YamlConfiguration getDataConfig() {
        return dataConfig;
    }

    private void createConfigs() {
        saveDefaultConfig();
        saveResource("data.yml", false);
        new File(this.getDataFolder().getAbsoluteFile() + "/lang").mkdirs();
        File file = new File(this.getDataFolder().getAbsoluteFile() + "/bosses");
        if(file.mkdirs()) {
            saveResource("bosses/example.yml", false);
        }

        this.saveResource("lang/ru.yml", false);
        this.saveResource("lang/en.yml", false);
    }
}
