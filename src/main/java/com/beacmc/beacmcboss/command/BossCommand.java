package com.beacmc.beacmcboss.command;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.subcommand.SubCommandManager;
import com.beacmc.beacmcboss.command.subcommand.*;
import com.beacmc.beacmcboss.config.impl.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class BossCommand implements CommandExecutor {

    private final SubCommandManager manager;

    public BossCommand() {
        manager = new SubCommandManager();
        manager.registerSubCommands(new BossReloadSubCommand(), new BossStartSubCommand(), new BossStopSubCommand(), new BossTpSubCommand(), new BossAddItemSubCommand(), new BossRemoveItemSubCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if(!sender.hasPermission("beacmcbosses.use")) {
            sender.sendMessage(Message.of(lang.getNoPermissionMessage()));
            return false;
        }

        if(args.length < 1) {
            sender.sendMessage(Message.of(lang.getNoArgsMessage()));
            return false;
        }

        manager.executeSubCommands(sender, args);
        return true;
    }
}
