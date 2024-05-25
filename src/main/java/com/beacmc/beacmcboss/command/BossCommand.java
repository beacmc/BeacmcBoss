package com.beacmc.beacmcboss.command;

import com.beacmc.beacmcboss.BeacmcBoss;
import com.beacmc.beacmcboss.boss.Boss;
import com.beacmc.beacmcboss.boss.config.BossConfig;
import com.beacmc.beacmcboss.boss.manager.BossManager;
import com.beacmc.beacmcboss.config.LanguageConfig;
import com.beacmc.beacmcboss.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BossCommand implements CommandExecutor {

    private final BeacmcBoss plugin;
    private final BossManager bossManager;

    public BossCommand() {
        plugin = BeacmcBoss.getInstance();
        bossManager = BeacmcBoss.getBossManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final LanguageConfig lang = BeacmcBoss.getLanguageConfig();

        if(!sender.hasPermission("beacmcbosses.use")) {
            sender.sendMessage(Message.of(lang.getNoPermissionMessage()));
            return false;
        }

        if(args.length != 2) {
            sender.sendMessage(Message.of(lang.getNoArgsMessage()));
            return false;
        }

        Boss boss = bossManager.getBossByName(args[1]);

        if (boss == null) {
            sender.sendMessage(Message.of(lang.getBossNotFoundMessage()));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "start": {
                if(boss.isSpawned()) {
                    sender.sendMessage(Message.of(lang.getBossAlreadySpawnedMessage()));
                    return false;
                }

                sender.sendMessage(Message.of(lang.getBossSpawnedMessage()));
                boss.spawn();
                return true;
            }

            case "stop": {
                if(!boss.isSpawned()) {
                    sender.sendMessage(Message.of(lang.getBossNotSpawnedMessage()));
                    return false;
                }

                sender.sendMessage(Message.of(lang.getBossStoppedMessage()));
                boss.despawn(null);
                return true;
            }
            case "tp": {
                if(!(sender instanceof Player player)) {
                    sender.sendMessage("only player");
                    return false;
                }

                if(!boss.isSpawned()) {
                    sender.sendMessage(Message.of(lang.getBossNotSpawnedMessage()));
                    return false;
                }

                player.sendMessage(Message.of(lang.getBossTpMessage()));
                player.teleport(boss.getLivingEntity().getLocation());
                return true;
            }
        }

        return true;
    }
}
