package com.beacmc.beacmcboss.boss.config;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BossConfig {

    private YamlConfiguration config;

    public BossConfig(YamlConfiguration config) {
        this.config = config;
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

    public YamlConfiguration getYamlConfiguration() {
        return config;
    }
}
