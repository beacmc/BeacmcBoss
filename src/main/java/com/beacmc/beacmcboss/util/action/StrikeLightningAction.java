package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrikeLightningAction implements Action {

    private final Pattern LOCATION_PATTERN = Pattern.compile("\\[location=([^;]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+)\\]");
    private final Logger logger;

    public StrikeLightningAction() {
        this.logger = BeacmcBoss.getInstance().getLogger();
    }

    @Override
    public String getName() {
        return "[strike_lightning]";
    }

    @Override
    public String getDescription() {
        return "will strike a specified location with lightning";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        final Matcher locationMatcher = LOCATION_PATTERN.matcher(param);

        if (!locationMatcher.find()) {
            logger.warning("Location not found");
            return;
        }

        String worldName = locationMatcher.group(1);
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            logger.severe("world cannot be found: " + worldName);
            return;
        }

        double x, y, z;
        try {
            x = Double.parseDouble(locationMatcher.group(2));
            y = Double.parseDouble(locationMatcher.group(3));
            z = Double.parseDouble(locationMatcher.group(4));
        } catch (NumberFormatException | NullPointerException e) {
            logger.severe("Was expected to be a number, but you set a different character.");
            return;
        }

        Location location = new Location(world, x, y, z);
        world.strikeLightning(location);
    }
}
