package com.beacmc.beacmcboss.boss.config;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BossConfig {

    private YamlConfiguration config;
    private File file;
    private final BeacmcBoss plugin;
    private final Logger logger;

    public BossConfig(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        plugin = BeacmcBoss.getInstance();
        logger = plugin.getLogger();
    }

    public String getDisplayName() {
        return config.getString("boss.display-name");
    }

    public double getCustomDamage() {
        return config.getDouble("boss.damage", 6.0);
    }

    public Location getSpawnLocation() {
        return config.getLocation("boss.spawn-location");
    }

    public List<ConfigurationSection> getTriggers() {
        ConfigurationSection section = config.getConfigurationSection("triggers");
        if (section == null)
            return Collections.emptyList();

        return section.getKeys(false).stream()
                .map(section::getConfigurationSection)
                .collect(Collectors.toList());
    }

    public int getSpawnTimePeriod() {
        return config.getInt("boss.time-start-period");
    }

    public double getHealth() {
        return config.getDouble("boss.health");
    }

    public EntityType getEntityType() {
        try {
            return EntityType.valueOf(config.getString("boss.entity-type"));
        } catch (IllegalArgumentException e) {
            logger.severe("An error occurred during boss spawn. Reason: invalid entity type");
        }
        return null;
    }

    public BarStyle getBarStyle() {
        try {
            return BarStyle.valueOf(config.getString("boss.boss-bar.style"));
        } catch (IllegalArgumentException e) {
            logger.severe("Unknown bar style");
        }
        return null;
    }

    public BarColor getBarColor() {
        try {
            return BarColor.valueOf(config.getString("boss.boss-bar.color"));
        } catch (IllegalArgumentException e) {
            logger.severe("Unknown bar color");
        }
        return null;
    }

    public String getBarTitle() {
        return config.getString("boss.boss-bar.text");
    }

    public boolean isBarEnable() {
        return config.getBoolean("boss.boss-bar.enable", false);
    }

    public YamlConfiguration getYamlConfiguration() {
        return config;
    }

    public File getFile() {
        return file;
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) { }
    }
}
