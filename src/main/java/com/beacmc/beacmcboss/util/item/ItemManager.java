package com.beacmc.beacmcboss.util.item;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, ItemStack> items;
    private final YamlConfiguration itemsConfig;
    private final File file;

    public ItemManager() {
        items = new HashMap<>();
        itemsConfig = BeacmcBoss.getItemsConfig();
        file = new File(BeacmcBoss.getInstance().getDataFolder(), "items.yml");
        load();
    }

    public void load() {
        final ConfigurationSection section = itemsConfig.getConfigurationSection("items");

        section.getKeys(false).forEach(item -> {
            if (section.isItemStack(item)) {
                items.put(item, section.getItemStack(item));
            }
        });
        System.out.println(items);
    }

    public boolean exists(ItemStack stack) {
        final ConfigurationSection section = itemsConfig.getConfigurationSection("items");
        return section.getKeys(false).stream()
                .anyMatch(s -> section.getItemStack(s) == stack);
    }

    public boolean exists(String name) {
        final ConfigurationSection section = itemsConfig.getConfigurationSection("items");
        return section.getKeys(false).contains(name);
    }

    public boolean addItem(String name, ItemStack item) {
        if (this.exists(name)) {
            return false;
        }
        loadConfig();
        itemsConfig.set("items." + name, item);
        saveConfig();
        items.put(name, item);
        return true;
    }

    public boolean removeItem(String name) {
        if (!this.exists(name)) {
            return false;
        }
        loadConfig();
        itemsConfig.set("items." + name, null);
        saveConfig();
        items.remove(name);
        return true;
    }

    public ItemStack getItemByName(String name) {
        return items.get(name);
    }

    private void loadConfig() {
        try {
            itemsConfig.load(file);
        } catch (IOException | InvalidConfigurationException ignored) { }
    }

    private void saveConfig() {
        try {
            itemsConfig.save(file);
        } catch (IOException ignored) { }
    }

    public Map<String, ItemStack> getItems() {
        return items;
    }
}
