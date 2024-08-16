package com.beacmc.beacmcboss.hook.papi;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Expansion extends PlaceholderExpansion {

    private final BeacmcBoss plugin;
    private final BossManager manager;

    public Expansion() {
        this.plugin = BeacmcBoss.getInstance();
        this.manager = BeacmcBoss.getBossManager();
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
        if(args.length != 2)
            return null;

        Boss boss = manager.getBossByName(args[0]);
        if(boss == null)
            return null;

        final LivingEntity entity = boss.getLivingEntity();
        final Location location = entity == null ? null : entity.getLocation();

        String s = null;

        try {
            switch (args[1].toLowerCase()) {
                case "display-name" : {
                    s = boss.getDisplayName();
                    break;
                }

                case "default-name" : {
                    s = boss.getName();
                    break;
                }

                case "time-to-start" : {
                    s = String.valueOf(boss.getTimeToStart());
                    break;
                }

                case "lifetime" : {
                    s = String.valueOf(boss.getLifetime());
                    break;
                }

                case "health" : {
                    s = String.valueOf((int) entity.getHealth());
                    break;
                }

                case "loc-x" : {
                    s = String.valueOf(location.getBlockX());
                    break;
                }

                case "loc-y" : {
                    s = String.valueOf(location.getBlockY());
                    break;
                }

                case "loc-z" : {
                    s = String.valueOf(location.getBlockZ());
                    break;
                }

                case "loc-world" : {
                    s = location.getWorld().getName();
                    break;
                }

                case "loc" : {
                    s = location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
                    break;
                }

                case "is-spawned" : {
                    s = String.valueOf(boss.isSpawned());
                    break;
                }

                case "last-killer" : {
                    s = boss.getLastKiller();
                    break;
                }

                default : {
                    s = null;
                    break;
                }
            }
        } catch (NullPointerException e) { }
        return s;
    }
}
