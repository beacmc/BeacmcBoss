package com.beacmc.beacmcboss;

import com.beacmc.beacmcboss.api.action.ActionManager;
import com.beacmc.beacmcboss.api.addon.AddonManager;
import com.beacmc.beacmcboss.api.requirement.RequirementManager;
import com.beacmc.beacmcboss.api.trigger.TriggerManager;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.command.BossCommand;
import com.beacmc.beacmcboss.config.ConfigManager;
import com.beacmc.beacmcboss.config.impl.BaseConfig;
import com.beacmc.beacmcboss.config.impl.LanguageConfig;
import com.beacmc.beacmcboss.data.DataManager;
import com.beacmc.beacmcboss.data.DatabaseConnector;
import com.beacmc.beacmcboss.hook.papi.Expansion;
import com.beacmc.beacmcboss.lib.LibraryLoader;
import com.beacmc.beacmcboss.listener.BossListener;
import com.beacmc.beacmcboss.listener.PlayerListener;
import com.beacmc.beacmcboss.util.Color;
import com.beacmc.beacmcboss.util.action.*;
import com.beacmc.beacmcboss.util.item.ItemManager;
import com.beacmc.beacmcboss.util.requirement.BooleanRequirement;
import com.beacmc.beacmcboss.util.requirement.ConditionRequirement;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public final class BeacmcBoss extends JavaPlugin {

    private static BeacmcBoss instance;
    private static ActionManager actionManager;
    private static RequirementManager requirementManager;
    private static TriggerManager triggerManager;
    private static BossManager bossManager;
    private static LanguageConfig languageConfig;
    private static BaseConfig baseConfig;
    private static Expansion expansion;
    private static AddonManager addonManager;
    private static YamlConfiguration localeConfig;
    private static YamlConfiguration itemsConfig;
    private static ItemManager itemManager;
    private static LibraryLoader libraryLoader;
    private static DatabaseConnector databaseConnector;
    private static DataManager dataManager;

    @Override
    public void onEnable() {
        long time = System.currentTimeMillis();
        instance = this;
        libraryLoader = new LibraryLoader();
        createConfigs();
        baseConfig = new BaseConfig();
        itemsConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "items.yml"));
        languageConfig = new LanguageConfig();
        ConfigManager baseConfigManager = new ConfigManager(new File(getDataFolder(), "config.yml"), getConfig());
        baseConfigManager.loadConfig(baseConfig);
        localeConfig = YamlConfiguration.loadConfiguration(baseConfig.getLocaleFile());
        ConfigManager localeConfigManager = new ConfigManager(baseConfig.getLocaleFile(), localeConfig);
        localeConfigManager.loadConfig(languageConfig);

        databaseConnector = new DatabaseConnector();
        databaseConnector.init();

        dataManager = new DataManager(databaseConnector);

        actionManager = new ActionManager();
        requirementManager = new RequirementManager();
        triggerManager = new TriggerManager();
        bossManager = new BossManager();

        itemManager = new ItemManager();

        actionManager.registerActions(new ParticleAction(), new BossStartAction(), new BossStopAction(), new BossEquipAction(), new BroadcastSoundAction(), new DamageAction(), new FireAction(), new SummonAction(), new ActionbarAction(), new TitleAction(), new BroadcastAction(), new SoundAction(), new ConsoleAction(), new MessageAction(), new DropItemAction(), new StrikeLightningAction());
        requirementManager.registerRequirements(new BooleanRequirement(), new ConditionRequirement());

        addonManager = new AddonManager();
        addonManager.loadAddons();

        expansion = new Expansion();
        expansion.register();
        this.getServer().getPluginManager().registerEvents(new BossListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getCommand("boss").setExecutor(new BossCommand());
        long finalTime = System.currentTimeMillis() - time;
        this.send("&7");
        this.send("#ffbb00██████╗░███████╗░█████╗░░█████╗░███╗░░░███╗░█████╗░██████╗░░█████╗░░██████╗░██████╗");
        this.send("#ffbb00██╔══██╗██╔════╝██╔══██╗██╔══██╗████╗░████║██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔════╝");
        this.send("#ffbb00██████╦╝█████╗░░███████║██║░░╚═╝██╔████╔██║██║░░╚═╝██████╦╝██║░░██║╚█████╗░╚█████╗░");
        this.send("#ffbb00██╔══██╗██╔══╝░░██╔══██║██║░░██╗██║╚██╔╝██║██║░░██╗██╔══██╗██║░░██║░╚═══██╗░╚═══██╗");
        this.send("#ffbb00██████╦╝███████╗██║░░██║╚█████╔╝██║░╚═╝░██║╚█████╔╝██████╦╝╚█████╔╝██████╔╝██████╔╝");
        this.send("#ffbb00╚═════╝░╚══════╝╚═╝░░╚═╝░╚════╝░╚═╝░░░░░╚═╝░╚════╝░╚═════╝░░╚════╝░╚═════╝░╚═════╝░");
        this.send("&7");
        this.send("&7  &fLoaded actions: #FBA408" + actionManager.getRegisterActions().size() + "&7  &7|  &fLoaded requirements: #FBA408" + requirementManager.getRegisterRequirements().size() + "&7  &7|  &fLoaded bosses: #FBA408" + bossManager.getRegisterBosses().values().size());
        this.send("&7  &fPlugin launched in #FBA408" + finalTime + " &fms");
        this.send("&7");
    }

    @Override
    public void onDisable() {
        addonManager.unloadAddons();
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

    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    public static ItemManager getItemManager() {
        return itemManager;
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

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static LibraryLoader getLibraryLoader() {
        return libraryLoader;
    }

    public static YamlConfiguration getLocaleConfig() {
        return localeConfig;
    }

    public static YamlConfiguration getItemsConfig() {
        return itemsConfig;
    }

    private void createConfigs() {
        saveDefaultConfig();
        saveResource("data.yml", false);
        saveResource("items.yml", false);
        new File(this.getDataFolder().getAbsoluteFile() + "/lang").mkdirs();
        File file = new File(this.getDataFolder().getAbsoluteFile() + "/bosses");
        if (file.mkdirs()) {
            saveResource("bosses/example.yml", false);
        }

        CompletableFuture.supplyAsync(() -> {
            this.saveResource("lang/ru.yml", false);
            this.saveResource("lang/en.yml", false);
            this.saveResource("lang/cs.yml", false);
            this.saveResource("lang/de.yml", false);
            this.saveResource("lang/es.yml", false);
            this.saveResource("lang/fr.yml", false);
            this.saveResource("lang/it.yml", false);
            this.saveResource("lang/pl.yml", false);
            this.saveResource("lang/pt.yml", false);
            this.saveResource("lang/ua.yml", false);
            return null;
        });
    }

    public void reload() {
        this.createConfigs();
        this.reloadConfig();
        baseConfig = new BaseConfig();
        languageConfig = new LanguageConfig();
        ConfigManager baseConfigManager = new ConfigManager(new File(getDataFolder(), "config.yml"), getConfig());
        baseConfigManager.loadConfig(baseConfig);
        localeConfig = YamlConfiguration.loadConfiguration(baseConfig.getLocaleFile());
        ConfigManager localeConfigManager = new ConfigManager(baseConfig.getLocaleFile(), localeConfig);
        localeConfigManager.loadConfig(languageConfig);
        addonManager.unloadAddons();
        addonManager.loadAddons();
        bossManager.unregisterAll();
        bossManager.loadBosses();
    }

    private void send(String message) {
        Bukkit.getConsoleSender().sendMessage(Color.of(message));
    }
}
