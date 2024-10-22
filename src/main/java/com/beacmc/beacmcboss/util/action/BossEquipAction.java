package com.beacmc.beacmcboss.util.action;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.action.Action;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.util.item.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BossEquipAction extends Action {

    private final Pattern TYPE_PATTERN = Pattern.compile("\\[type=(\\w+)]");
    private final Pattern ITEM_PATTERN = Pattern.compile("\\[item=(\\w+)]");
    private final ItemManager manager;
    private final Logger logger;

    public BossEquipAction() {
        this.logger = BeacmcBoss.getInstance().getLogger();
        this.manager = BeacmcBoss.getItemManager();
    }

    @Override
    public String getName() {
        return "[equip]";
    }

    @Override
    public String getDescription() {
        return "gives the boss armor or an item to hold";
    }

    @Override
    public void execute(Player player, Boss boss, String param) {
        final EntityEquipment equip = boss.getLivingEntity().getEquipment();
        final Matcher typeMatcher = TYPE_PATTERN.matcher(param);
        final Matcher itemMatcher = ITEM_PATTERN.matcher(param);

        if (!typeMatcher.find() || !itemMatcher.find()) {
            logger.warning("Armor type or material unknown.");
            logger.warning("- action is stopped by force.");
            return;
        }

        try {
            ItemStack stack = manager.getItemByName(itemMatcher.group(1));
            if (stack == null) {
                logger.warning("Item not found");
                return;
            }
            String type = typeMatcher.group(1).toUpperCase();
            switch (type) {
                case "HELMET": {
                    equip.setHelmet(stack);
                    break;
                }
                case "CHESTPLATE": {
                    equip.setChestplate(stack);
                    break;
                }
                case "LEGGINGS": {
                    equip.setLeggings(stack);
                    break;
                }
                case "BOOTS": {
                    equip.setBoots(stack);
                    break;
                }
                case "HAND": {
                    equip.setItemInMainHand(stack);
                    break;
                }
                case "OFFHAND": {
                    equip.setItemInOffHand(stack);
                    break;
                }
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            logger.warning("this material was not found");
        }
    }
}
