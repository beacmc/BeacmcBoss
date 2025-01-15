package com.beacmc.beacmcboss.config;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;

public class ConfigManager {

    private final FileConfiguration config;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
    }

    public void loadConfig(Object object) {
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigValue.class))
                continue;

            ConfigValue annotation = field.getAnnotation(ConfigValue.class);
            String key = annotation.key();

            field.setAccessible(true);
            try {
                Object value = config.get(key);

                if (field.getType() == String.class) {
                    field.set(object, value != null ? value.toString() : null);
                }

                else if (field.getType() == int.class) {
                    field.set(object, value != null ? value : 0);
                }

                else if (field.getType() == boolean.class) {
                    field.set(object, value != null ? value : false);
                }

                else if (field.getType() == double.class) {
                    field.set(object, value != null ? value : 0.0);
                }

                else if (field.getType() == File.class) {
                    File path = new File(BeacmcBoss.getInstance().getDataFolder(), "lang");
                    field.set(object, new File(value != null ? path.getAbsolutePath() + File.separator + value : ""));
                }

                else if (field.getType() == ConfigurationSection.class) {
                    field.set(object, config.getConfigurationSection(key));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
