package com.beacmc.beacmcboss.hook.papi;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.data.DataManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Expansion extends PlaceholderExpansion {

    private final BeacmcBoss plugin;
    private final BossManager manager;
    private final DataManager dataManager;

    public Expansion() {
        this.plugin = BeacmcBoss.getInstance();
        this.manager = BeacmcBoss.getBossManager();
        this.dataManager = BeacmcBoss.getDataManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "beacmcboss";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "beacmc";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        String[] args = params.split(":");
        if (args.length < 2) {
            String result = null;
            switch (params) {
                case "boss-kills" -> result = String.valueOf(dataManager.getBossKills(player.getUniqueId()));
                case "total-damage" -> result = String.valueOf(dataManager.getTotalDamage(player.getUniqueId()));
            }
            return result;
        }

        Boss boss = manager.getBossByName(args[0]);
        if(boss == null)
            return null;

        final LivingEntity entity = boss.getLivingEntity();
        final Location location = entity == null ? null : entity.getLocation();

        String s = "";

        try {
            switch (args[1].toLowerCase()) {
                case "display-name" -> s = boss.getDisplayName();
                case "default-name" -> s = boss.getName();
                case "time-to-start" -> s = String.valueOf(boss.getTimeToStart());
                case "lifetime" -> s = String.valueOf(boss.getLifetime());
                case "health" -> s = String.valueOf((int) entity.getHealth());
                case "loc-x" -> s = String.valueOf(location.getBlockX());
                case "loc-y" -> s = String.valueOf(location.getBlockY());
                case "loc-z" -> s = String.valueOf(location.getBlockZ());
                case "loc-world" -> s = location.getWorld().getName();
                case "loc" -> s = location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
                case "is-spawned" -> s = String.valueOf(boss.isSpawned());
                case "last-killer" -> s = boss.getLastKiller();
            }
        } catch (NullPointerException ignored) { }
        return s;
    }
}
