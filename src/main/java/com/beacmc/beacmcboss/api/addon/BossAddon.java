package com.beacmc.beacmcboss.api.addon;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BossAddon {

    private String name;
    private String version;
    private File file;
    private ClassLoader classLoader;
    private URLClassLoader urlClassLoader;
    private BeacmcBoss beacmcBoss;
    private boolean enabled;

    public BossAddon() {}

    public final void initialize(String name, String version, File file, URLClassLoader urlClassLoader) {
        this.name = name;
        this.version = version;
        this.file = file;
        this.beacmcBoss = BeacmcBoss.getInstance();
        this.urlClassLoader = urlClassLoader;
        classLoader = getClass().getClassLoader();
    }

    public void onEnable() {}


    public void onDisable() {}

    public InputStream getResource(String file) {
        try {
            URL url = classLoader.getResource(file);

            if(url != null) {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }

        } catch (IOException e) {}
        return null;
    }

    public void saveResource(String file) {
        try (InputStream inputStream = getResource(file)) {
            if (inputStream == null)
                return;

            Path targetPath = Paths.get(file);
            Files.createDirectories(targetPath.getParent());

            if (Files.exists(targetPath))
                return;

            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public URLClassLoader getUrlClassLoader() {
        return urlClassLoader;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public File getDataFolder() {
        File dir = new File(beacmcBoss.getDataFolder(), "addons" + File.separator + name);
        if(!dir.exists())
            dir.mkdirs();

        return dir;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;

            if (this.enabled)
                onEnable();

            else
                onDisable();
        }
    }

    public BeacmcBoss getBeacmcBoss() {
        return beacmcBoss;
    }
}
