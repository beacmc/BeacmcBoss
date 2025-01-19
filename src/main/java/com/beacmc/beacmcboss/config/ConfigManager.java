package com.beacmc.beacmcboss.config;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Field;

public class ConfigManager {

    private final FileConfiguration config;
    private final File file;

    public ConfigManager(File file, FileConfiguration config) {
        this.config = config;
        this.file = file;
    }

    public void loadConfig(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigValue.class))
                continue;

            ConfigValue annotation = field.getAnnotation(ConfigValue.class);
            String key = annotation.key();

            field.setAccessible(true);
            try {
                Object value = config.get(key);

                if (value == null || !config.isSet(key)) {
                    if (annotation.setDefaultValueOnNull()) {
                        config.set(key, annotation.defaultValue());
                    }
                    continue;
                }

                if (field.getType() == File.class) {
                    File path = new File(BeacmcBoss.getInstance().getDataFolder(), "lang");
                    field.set(object, new File( path.getAbsolutePath() + File.separator + value));
                } else if (field.getType() == ConfigurationSection.class) {
                    field.set(object, config.getConfigurationSection(key));
                } else if (field.getType().isEnum()) {
                    Class<Enum> enumType = (Class<Enum>) field.getType();
                    Enum<?> enumValue = Enum.valueOf(enumType, value.toString().toUpperCase());
                    field.set(object, enumValue);
                } else {
                    field.set(object, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        saveConfig();
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
