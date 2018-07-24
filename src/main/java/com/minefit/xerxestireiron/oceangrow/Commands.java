package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {
    private final OceanGrow plugin;

    public Commands(OceanGrow instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!command.getName().equalsIgnoreCase("mendthis") || !(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("mendanywhere.mend")) {
            player.sendMessage("You are not allowed to mend items that way.");
            return true;
        }

        ItemStack objectInHand = player.getInventory().getItemInMainHand();

        return true;
    }
}
