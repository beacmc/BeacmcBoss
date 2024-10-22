package com.beacmc.beacmcboss.command.subcommand;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.api.subcommand.SubCommand;
import com.beacmc.beacmcboss.config.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BossTpSubCommand extends SubCommand {

    private final BossManager bossManager;

    public BossTpSubCommand() {
        bossManager = BeacmcBoss.getBossManager();
    }

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if(args.length < 2) {
            sender.sendMessage(Message.of(lang.getNoArgsMessage()));
            return;
        }

        if(!(sender instanceof Player)) {
            sender.sendMessage("only player");
            return;
        }

        Player player = (Player) sender;

        Boss boss = bossManager.getBossByName(args[1]);


        if (boss == null) {
            sender.sendMessage(Message.of(lang.getBossNotFoundMessage()));
            return;
        }

        if(!boss.isSpawned()) {
            sender.sendMessage(Message.of(lang.getBossNotSpawnedMessage()));
            return;
        }

        player.sendMessage(Message.of(lang.getBossTpMessage()));
        player.teleport(boss.getLivingEntity().getLocation());
    }
}
