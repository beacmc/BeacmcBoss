package com.beacmc.beacmcboss.lib;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import com.beacmc.beacmcboss.BeacmcBoss;

import java.util.Arrays;

public class LibraryLoader {

    private final BukkitLibraryManager manager;

    public LibraryLoader() {
        manager = new BukkitLibraryManager(BeacmcBoss.getInstance());
        manager.addMavenCentral();
        manager.addSonatype();
        manager.addJitPack();
    }

    public void loadLibrary(Library library) {
        manager.loadLibrary(library);
    }

    public void loadLibraries(Library... libraries) {
        Arrays.stream(libraries).forEach(this::loadLibrary);
    }

    public void addRepository(String url) {
        manager.addRepository(url);
    }
}
