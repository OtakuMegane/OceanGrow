package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
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
            World world = event.getPlayer().getWorld();
            ItemStack usedItem = event.getItem();
            Block block = event.getClickedBlock();
            String growConfig = "OceanGrow.worlds." + block.getWorld().getName() + ".";

            if (usedItem.getType() == Material.STICK) {
                if (usedItem.getItemMeta().getDisplayName().equals("oceangrow-kelp-wand")) {
                    this.grow.growFunction(world, block, Material.KELP_PLANT);
                }

                if (usedItem.getItemMeta().getDisplayName().equals("oceangrow-seagrass-wand")) {
                    this.grow.growFunction(world, block, Material.SEAGRASS);
                }
            }

        }
    }
}
