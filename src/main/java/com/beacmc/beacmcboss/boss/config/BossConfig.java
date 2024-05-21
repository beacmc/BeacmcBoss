package com.beacmc.beacmcboss.boss.config;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BossConfig {

    private YamlConfiguration config;
    private final BeacmcBoss plugin;
    private final Logger logger;

    public BossConfig(YamlConfiguration config) {
        this.config = config;
        plugin = BeacmcBoss.getInstance();
        logger = plugin.getLogger();
    }

    public String getDisplayName() {
        return config.getString("boss.display-name");
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

    public YamlConfiguration getYamlConfiguration() {
        return config;
    }
}
