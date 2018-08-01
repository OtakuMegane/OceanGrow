package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private final OceanGrow plugin;
    private final Grow grow;

    PlayerListener(OceanGrow plugin, Grow grow) {
        this.plugin = plugin;
        this.grow = grow;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem() && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            World world = player.getWorld();
            ItemStack usedItem = event.getItem();
            Block block = event.getClickedBlock();

            if (usedItem.getType() == Material.STICK) {
                if (usedItem.getItemMeta().getDisplayName().equals("oceangrow-kelp-wand")) {
                    if (player.hasPermission("oceangrow.usewand")) {
                        player.sendMessage("Now planting kelp...");
                        this.grow.growFunction(world, block, Material.KELP_PLANT);
                        player.sendMessage("Done planting kelp!");
                    } else {
                        player.sendMessage("You are not allowed to use the kelp wand.");
                    }
                } else if (usedItem.getItemMeta().getDisplayName().equals("oceangrow-seagrass-wand")) {
                    if (player.hasPermission("oceangrow.usewand")) {
                        player.sendMessage("Now planting seagrass...");
                        this.grow.growFunction(world, block, Material.SEAGRASS);
                        player.sendMessage("Done planting seagrass!");
                    } else {
                        player.sendMessage("You are not allowed to use the seagrass wand.");
                    }
                }
            }
        }
    }
}
