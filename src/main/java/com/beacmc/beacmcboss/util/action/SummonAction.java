package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SummonAction extends Action {

    private final Pattern ENTITY_PATTERN = Pattern.compile("\\[entity=([A-Z_]+)\\]");
    private final Pattern LOCATION_PATTERN = Pattern.compile("\\[location=([^;]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+)\\]");


    @Override
    public String getName() {
        return "[summon]";
    }

    @Override
    public String getDescription() {
        return "summon a mob to a location";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        final Logger logger = BeacmcBoss.getInstance().getLogger();

        Matcher locationMatcher = LOCATION_PATTERN.matcher(param);
        Matcher entityMatcher = ENTITY_PATTERN.matcher(param);

        if (!locationMatcher.find() || !entityMatcher.find())
            return;

        String name = entityMatcher.group(1);
        World world = Bukkit.getWorld(locationMatcher.group(1));

        if (world == null) {
            return;
        }


        double x, y, z;

        try {
            x = Double.parseDouble(locationMatcher.group(2));
            y = Double.parseDouble(locationMatcher.group(3));
            z = Double.parseDouble(locationMatcher.group(4));
        } catch (NumberFormatException e) {
            return;
        }

        Location location = new Location(world, x, y, z);

        try {
            EntityType type = EntityType.valueOf(name.toUpperCase());
            world.spawnEntity(location, type);
        } catch (IllegalArgumentException e) { }
    }
}
