package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Commands implements CommandExecutor {
    private final OceanGrow plugin;

    public Commands(OceanGrow instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        if (!command.getName().equalsIgnoreCase("oceangrow") || !(sender instanceof Player) || arguments.length < 1) {
            return false;
        }

        Player player = (Player) sender;

        if (arguments[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("oceangrow.reload")) {
                this.plugin.reload();
                player.sendMessage("OceanGrow config reloaded.");
                return true;
            } else {
                player.sendMessage("You are not allowed to reload OceanGrow.");
                return true;
            }
        } else if (arguments[0].equalsIgnoreCase("kelp-wand")) {
            if (player.hasPermission("oceangrow.usewand")) {
                ItemStack wand = new ItemStack(Material.STICK);
                ItemMeta wandMeta = wand.getItemMeta();
                wandMeta.setDisplayName("oceangrow-kelp-wand");
                wand.setItemMeta(wandMeta);
                player.getInventory().addItem(wand);
                player.sendMessage("You have received a kelp wand!");
                return true;
            } else {
                player.sendMessage("You are not allowed to use that wand.");
                return true;
            }
        } else if (arguments[0].equalsIgnoreCase("seagrass-wand")) {
            if (player.hasPermission("oceangrow.usewand")) {
                ItemStack wand = new ItemStack(Material.STICK);
                ItemMeta wandMeta = wand.getItemMeta();
                wandMeta.setDisplayName("oceangrow-seagrass-wand");
                wand.setItemMeta(wandMeta);
                player.getInventory().addItem(wand);
                player.sendMessage("You have received a seagrass wand!");
                return true;
            } else {
                player.sendMessage("You are not allowed to use that wand.");
                return true;
            }
        }

        return false;
    }
}
