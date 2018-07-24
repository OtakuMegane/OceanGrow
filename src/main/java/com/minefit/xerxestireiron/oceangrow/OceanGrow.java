package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OceanGrow extends JavaPlugin implements Listener {
    private final Commands commands = new Commands(this);
    private final Grow grow = new Grow(this);
    private final PlayerListener playerListener = new PlayerListener(this, grow);

    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this.playerListener, this);
        getCommand("oceangrow").setExecutor(this.commands);
    }

    public void onDisable() {
    }
}
