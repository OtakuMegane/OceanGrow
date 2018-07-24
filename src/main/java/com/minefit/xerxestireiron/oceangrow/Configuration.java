package com.minefit.xerxestireiron.oceangrow;

import org.bukkit.configuration.ConfigurationSection;

public class Configuration {

    private final OceanGrow plugin;
    public final int defaultGrowRadius;
    public final int maximumGrowRadius;
    public final int defaultClusterRadius;
    public final int maximumClusterRadius;
    public final int clusterFactorKelp;
    public final int clusterFactorSeagrass;
    private int currentGrowRadius;
    private int currentClusterRadius;
    private int seagrassDensity;
    private int kelpDensity;

    public Configuration(OceanGrow instance) {
        this.plugin = instance;
        ConfigurationSection rawConfig = this.plugin.getConfig();
        this.defaultGrowRadius = rawConfig.getInt("grow_radius.default", 10);
        this.maximumGrowRadius = rawConfig.getInt("grow_radius.maximum", 100);
        this.currentGrowRadius = (this.defaultGrowRadius <= this.maximumGrowRadius) ? this.defaultGrowRadius
                : this.maximumGrowRadius;
        this.defaultClusterRadius = rawConfig.getInt("cluster_radius.default", 10);
        this.maximumClusterRadius = rawConfig.getInt("cluster_radius.maximum", 25);
        this.currentClusterRadius = (this.defaultClusterRadius <= this.maximumClusterRadius) ? this.defaultClusterRadius
                : this.maximumClusterRadius;
        this.clusterFactorKelp = rawConfig.getInt("cluster_factor.kelp", 2);
        this.clusterFactorSeagrass = rawConfig.getInt("cluster_factor.seagrass", 10);
        this.kelpDensity = rawConfig.getInt("cluster_density.kelp", 20);
        this.seagrassDensity = rawConfig.getInt("cluster_density.seagrass", 20);

    }

    public int getDensity(String key) {
        if (key.equals("kelp")) {
            return this.kelpDensity;
        } else if (key.equals("seagrass")) {
            return this.seagrassDensity;
        } else {
            return 0;
        }
    }

    public boolean setDensity(String key, int density) {
        if (key.equals("kelp")) {
            this.kelpDensity = (density >= 0) ? density: this.kelpDensity;
            return true;
        } else if (key.equals("seagrass")) {
            this.seagrassDensity = (density >= 0) ? density: this.seagrassDensity;
            return true;
        } else {
            return false;
        }
    }

    public int getRadius(String key) {
        if (key.equals("grow")) {
            return this.currentGrowRadius;
        } else if (key.equals("cluster")) {
            return this.currentClusterRadius;
        } else {
            return 0;
        }
    }

    public boolean setRadius(String key, int radius) {
        if (key.equals("grow")) {
            if (radius > this.maximumGrowRadius)
                this.currentGrowRadius = (radius <= this.maximumGrowRadius) ? radius : this.maximumGrowRadius;
            return true;
        } else if (key.equals("cluster")) {
            this.currentClusterRadius = (radius <= this.maximumClusterRadius) ? radius : this.maximumClusterRadius;
            return true;
        } else {
            return false;
        }
    }

}
