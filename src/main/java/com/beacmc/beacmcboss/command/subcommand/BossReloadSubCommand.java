package com.beacmc.beacmcboss.command.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.api.subcommand.SubCommand;
import com.beacmc.beacmcboss.config.impl.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import org.bukkit.command.CommandSender;

public class BossReloadSubCommand implements SubCommand {

    private final BeacmcBoss plugin;

    public BossReloadSubCommand() {
        plugin = BeacmcBoss.getInstance();
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        plugin.reload();
        sender.sendMessage(Message.of(lang.getReloadMessage()));
    }
}
