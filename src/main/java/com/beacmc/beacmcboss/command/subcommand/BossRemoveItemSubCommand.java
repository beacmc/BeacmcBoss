package com.beacmc.beacmcboss.command.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.subcommand.SubCommand;
import com.beacmc.beacmcboss.config.impl.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import com.beacmc.beacmcboss.util.item.ItemManager;
import org.bukkit.command.CommandSender;

public class BossRemoveItemSubCommand implements SubCommand {

    private final ItemManager manager;

    public BossRemoveItemSubCommand() {
        manager = BeacmcBoss.getItemManager();
    }

    @Override
    public String getName() {
        return "removeitem";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if (args.length < 2) {
            sender.sendMessage(Message.of(lang.getNoArgsMessage()));
            return;
        }

        if (!manager.removeItem(args[1])) {
            sender.sendMessage(Message.of(lang.getItemNotFoundMessage()));
            return;
        }

        sender.sendMessage(Message.of(lang.getItemRemovedMessage()));
    }
}
