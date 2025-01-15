package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.item.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DropItemAction implements Action {

    private final Pattern LOCATION_PATTERN = Pattern.compile("\\[location=([^;]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+);([-+]?[0-9]*\\.?[0-9]+)\\]");
    private final Pattern ITEM_PATTERN = Pattern.compile("\\[item=(\\w+)]");
    private final Logger logger;
    private final ItemManager manager;

    public DropItemAction() {
        logger = BeacmcBoss.getInstance().getLogger();
        manager = BeacmcBoss.getItemManager();
    }

    @Override
    public String getName() {
        return "[drop]";
    }

    @Override
    public String getDescription() {
        return "Drops an item at the specified location";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        final Matcher itemMatcher = ITEM_PATTERN.matcher(param);
        final Matcher locationMatcher = LOCATION_PATTERN.matcher(param);

        if (!locationMatcher.find() || !itemMatcher.find()) {
            logger.warning("Location or item not found");
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

        ItemStack item = manager.getItemByName(itemMatcher.group(1));
        Location location = new Location(world, x, y, z);

        if (item == null) {
            logger.severe("Item not found");
            return;
        }

        world.dropItemNaturally(location, item);
    }
}
