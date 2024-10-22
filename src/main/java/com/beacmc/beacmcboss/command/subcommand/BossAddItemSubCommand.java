package com.beacmc.beacmcboss.command.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.subcommand.SubCommand;
import com.beacmc.beacmcboss.config.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import com.beacmc.beacmcboss.util.item.ItemManager;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BossAddItemSubCommand extends SubCommand {

    private final ItemManager manager;

    public BossAddItemSubCommand() {
        manager = BeacmcBoss.getItemManager();
    }

    @Override
    public String getName() {
        return "additem";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player");
            return;
        }

        final Player player = (Player) sender;
        final PlayerInventory inv = player.getInventory();
        final ItemStack item = inv.getItemInMainHand();

        if (args.length < 2) {
            player.sendMessage(Message.of(lang.getNoArgsMessage()));
            return;
        }

        if (item.getType() == Material.AIR) {
            player.sendMessage(Message.of(lang.getItemNullMessage()));
            return;
        }

        if (!manager.addItem(args[1], item)) {
            player.sendMessage(Message.of(lang.getItemAlreadyAddedMessage()));
            return;
        }

        player.sendMessage(Message.of(lang.getItemAddedMessage()));
    }
}
