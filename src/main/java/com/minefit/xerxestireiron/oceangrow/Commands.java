package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    private final OceanGrow plugin;

    public Commands(OceanGrow instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!command.getName().equalsIgnoreCase("oceangrow") || !(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (arguments[0].equalsIgnoreCase("reload")) {
            if(player.hasPermission("oceangrow.reload"))
            {
                ; //TODO: Reload
            } else {
                player.sendMessage("You are not allowed to reload OceanGrow.");
            }
        }
        else if(arguments[0].equalsIgnoreCase("kelp-wand"))
        {
            if(player.hasPermission("oceangrow.usewand"))
            {
                ; //TODO: Give wand
            } else {
                player.sendMessage("You are not allowed to use that wand.");
            }
        }
        else if(arguments[0].equalsIgnoreCase("seagrass-wand"))
        {
            if(player.hasPermission("oceangrow.usewand"))
            {
                ; //TODO: Give wand
            } else {
                player.sendMessage("You are not allowed to use that wand.");
            }
        }



        return true;
    }
}
