package com.beacmc.beacmcboss.api.addon;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonManager {

    private final Map<String, BossAddon> addons;

    public AddonManager() {
        addons = new HashMap<>();
    }

    public void loadAddons() {
        File addonsDir = new File(BeacmcBoss.getInstance().getDataFolder(), "addons");
        File[] files = addonsDir.listFiles();

        if(!addonsDir.exists()) {
            addonsDir.mkdirs();
        }

        if(files == null) return;

        for (File file : files) {
            loadAddon(file);
        }
    }

    public void loadAddon(File file) {
        if (!file.isFile() || !file.getName().endsWith(".jar")) return;

        try (JarFile jar = new JarFile(file)) {
            JarEntry entry = jar.getJarEntry("addon.yml");
            Yaml yaml = new Yaml();
            Map<String, Object> map = yaml.load(jar.getInputStream(entry));

            String name = (String) map.get("name");
            String version = (String) map.get("version");
            String main = (String) map.get("main");

            if (isLoad(name))
                return;

            URLClassLoader loader = new URLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
            try {
                Class<?> clazz = Class.forName(main, true, loader);
                BossAddon addon = (BossAddon) clazz.getDeclaredConstructor().newInstance();
                addon.initialize(name, version, file, loader);
                addons.put(name, addon);
                enableAddon(addon);
            } catch (Throwable e) {
                closeURLClassLoader(loader);
                addons.remove(name);
                throw new IllegalArgumentException("Main class not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableAddon(BossAddon addon) {
        addon.setEnabled(true);
    }

    public void disableAddon(BossAddon addon) {
        addon.setEnabled(false);
    }

    public void unloadAddons() {
        this.getAddons().forEach(this::unloadAddon);
    }

    public void unloadAddon(BossAddon addon) {
        try {
            disableAddon(addon);
            addons.remove(addon.getName());
            closeURLClassLoader(addon.getUrlClassLoader());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public boolean isLoad(BossAddon addon) {
        return addons.containsValue(addon);
    }

    public boolean isLoad(String addon) {
        return addons.containsKey(addon);
    }

    private void closeURLClassLoader(URLClassLoader loader) {
        try {
            loader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<BossAddon> getAddons() {
        return addons.values();
    }
}
