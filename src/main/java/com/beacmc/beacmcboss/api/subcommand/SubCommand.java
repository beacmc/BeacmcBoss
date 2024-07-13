package com.beacmc.beacmcboss.api.subcommand;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getName();

    public abstract void execute(CommandSender sender, String[] args);
}
