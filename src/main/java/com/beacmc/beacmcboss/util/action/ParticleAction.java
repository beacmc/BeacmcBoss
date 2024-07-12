package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticleAction extends Action {


    private final Pattern PARTICLE_PATTERN = Pattern.compile("\\[settings=([^;]+);([0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+)\\]");
    private final Pattern LOCATION_PATTERN = Pattern.compile("\\[location=([^;]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+)\\]");


    @Override
    public String getName() {
        return "[particle]";
    }

    @Override
    public String getDescription() {
        return "create the particle in place";
    }

    @Override
    public void execute(Player player, Boss boss, String p) {
        final Logger logger = BeacmcBoss.getInstance().getLogger();
        final String param = PlaceholderAPI.setPlaceholders(player, p);

        Matcher locationMatcher = LOCATION_PATTERN.matcher(param);
        Matcher particleMatcher = PARTICLE_PATTERN.matcher(param);

        if (!locationMatcher.find() || !particleMatcher.find()) {
            logger.severe("location or particle not found");
            return;
        }

        String worldName = locationMatcher.group(1);
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            logger.severe("world cannot be found: " + worldName);
            return;
        }

        double x, y, z;
        double offsetX, offsetY, offsetZ, speed;
        int count;

        try {
            x = Double.parseDouble(locationMatcher.group(2));
            y = Double.parseDouble(locationMatcher.group(3));
            z = Double.parseDouble(locationMatcher.group(4));

            count = Integer.parseInt(particleMatcher.group(2));
            offsetX = Double.parseDouble(particleMatcher.group(3));
            offsetY = Double.parseDouble(particleMatcher.group(4));
            offsetZ = Double.parseDouble(particleMatcher.group(5));
            speed = Double.parseDouble(particleMatcher.group(6));
        } catch (NumberFormatException | NullPointerException e) {
            logger.severe("was expected to be a number, but you set a different character.");
            return;
        }

        Location location = new Location(world, x, y, z);
        Particle particleType = getParticleForName(particleMatcher.group(1));

        if (particleType == null) {
            logger.severe("Invalid particle type: " + particleMatcher.group(1));
            return;
        }

        location.getWorld().spawnParticle(particleType, location, count, offsetX, offsetY, offsetZ, speed);
    }

    private Particle getParticleForName(String name) {
        try {
            return Particle.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
