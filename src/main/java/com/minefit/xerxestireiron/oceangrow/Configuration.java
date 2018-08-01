package com.minefit.xerxestireiron.oceangrow;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;

public class Configuration {

    private final OceanGrow plugin;
    public final int growRadius;
    public final int kelpClusterFactor;
    public final int seagrassClusterFactor;
    public final int kelpClusterRadius;
    public final int seagrassClusterRadius;
    public final int seagrassDensity;
    public final int kelpDensity;
    public final List<NamespacedKey> growBlocks = new ArrayList<>();

    public Configuration(OceanGrow instance) {
        this.plugin = instance;
        ConfigurationSection rawConfig = this.plugin.getConfig();
        this.growRadius = rawConfig.getInt("grow-radius", 20);
        this.kelpClusterFactor = rawConfig.getInt("cluster-factor.kelp", 2);
        this.seagrassClusterFactor = rawConfig.getInt("cluster-factor.kelp", 3);
        this.kelpClusterRadius = rawConfig.getInt("cluster-radius.kelp", 10);
        this.seagrassClusterRadius = rawConfig.getInt("cluster-radius.seagrass", 10);
        this.kelpDensity = rawConfig.getInt("cluster-density.kelp", 20);
        this.seagrassDensity = rawConfig.getInt("cluster-density.seagrass", 20);

        for (String string_key : rawConfig.getStringList("grow-blocks")) {
            this.growBlocks.add(NamespacedKey.minecraft(string_key.substring(string_key.indexOf("minecraft:") + 10)));
        }
    }
}
