package com.beacmc.beacmcboss.command.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.api.subcommand.SubCommand;
import com.beacmc.beacmcboss.config.impl.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import org.bukkit.command.CommandSender;

public class BossStartSubCommand implements SubCommand {

    private final BossManager bossManager;

    public BossStartSubCommand() {
        bossManager = BeacmcBoss.getBossManager();
    }

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if(args.length < 2) {
            sender.sendMessage(Message.of(lang.getNoArgsMessage()));
            return;
        }

        Boss boss = bossManager.getBossByName(args[1]);


        if (boss == null) {
            sender.sendMessage(Message.of(lang.getBossNotFoundMessage()));
            return;
        }

        if(boss.isSpawned()) {
            sender.sendMessage(Message.of(lang.getBossAlreadySpawnedMessage()));
            return;
        }

        sender.sendMessage(Message.of(lang.getBossSpawnedMessage()));
        boss.spawn(bossManager.createSpawnLocation(boss));
    }
}
