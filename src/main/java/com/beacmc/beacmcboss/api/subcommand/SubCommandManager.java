package com.beacmc.beacmcboss.api.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

public class SubCommandManager {

    private final Set<SubCommand> registerSubCommands;

    public SubCommandManager() {
        registerSubCommands = new HashSet<>();
    }

    public void registerSubCommand(SubCommand sub) {
        if(isRegisterSubCommand(sub)) {
            return;
        }
        registerSubCommands.add(sub);
    }

    public void registerSubCommands(SubCommand... subs) {
        for(SubCommand sub : subs) {
            if (isRegisterSubCommand(sub)) {
                continue;
            }
            registerSubCommands.add(sub);
        }
    }

    public void unregisterSubCommand(SubCommand sub) {
        if(!isRegisterSubCommand(sub)) {
            return;
        }
        registerSubCommands.remove(sub);
    }

    public boolean isRegisterSubCommand(SubCommand sub) {
        String name = sub.getName();
        for (SubCommand execute : registerSubCommands) {
            if(name.equals(execute.getName()))
                return true;
        }
        return false;
    }

    public void executeSubCommands(CommandSender sender, String[] args) {
        for (String execute : args) {
            for (SubCommand sub : registerSubCommands) {
                String name = sub.getName();
                if (!execute.startsWith(name))
                    continue;

                sub.execute(sender, args);
            }
        }
    }

    public Set<SubCommand> getRegisterSubCommands() {
        return registerSubCommands;
    }
}
