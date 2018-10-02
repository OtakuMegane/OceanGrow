package com.minefit.xerxestireiron.oceangrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;

public class Grow {

    private final OceanGrow plugin;
    private Configuration config;

    public Grow(OceanGrow instance) {
        this.plugin = instance;
        initConfig();
    }

    public void initConfig() {
        this.config = null;
        this.config = new Configuration(this.plugin);
    }

    public void growFunction(World world, Block block, Material blockType) {
        Location blockLocation = block.getLocation();
        int x = blockLocation.getBlockX();
        int z = blockLocation.getBlockZ();
        int growRadius = config.growRadius;
        int extraFactor = blockType.name().charAt(0) + blockType.name().length();
        Random random = new Random(world.getSeed() + x + z + extraFactor);

        for (int x1 = 0 - growRadius; x1 <= growRadius; x1++) {
            for (int z1 = 0 - growRadius; z1 <= growRadius; z1++) {
                int clusterDensity = 0;
                int clusterRadius = 0;
                Location centerLocation = new Location(world, x + x1, 0, z + z1);

                if (!isExistingChunk(centerLocation)) {
                    continue;
                }

                Block centerBlock = block.getWorld().getBlockAt(centerLocation);
                int clusterChance = random.nextInt(1000);

                if (blockType == Material.KELP_PLANT) {
                    if (clusterChance >= config.kelpClusterFactor) {
                        continue;
                    }

                    clusterRadius = random.nextInt(config.kelpClusterRadius) + (config.kelpClusterRadius / 2);
                    clusterDensity = config.kelpDensity;
                } else if (blockType == Material.SEAGRASS) {
                    if (clusterChance >= config.seagrassClusterFactor) {
                        continue;
                    }

                    clusterRadius = random.nextInt(config.seagrassClusterRadius) + (config.seagrassClusterRadius / 2);
                    clusterDensity = config.seagrassDensity;
                }

                int lowX = centerBlock.getX() - clusterRadius;
                int lowZ = centerBlock.getZ() - clusterRadius;
                int highX = centerBlock.getX() + clusterRadius;
                int highZ = centerBlock.getZ() + clusterRadius;
                int clusterDiameter = clusterRadius * 2;
                int clusterArea = clusterDiameter * clusterDiameter;
                int finalDensity = (int) (clusterArea * (clusterDensity / 100F));

                for (int i = 0; i <= finalDensity; ++i) {
                    int xRand = random.nextInt(clusterDiameter);
                    int zRand = random.nextInt(clusterDiameter);

                    Location plantLocation = new Location(world, lowX + xRand, 0, lowZ + zRand);

                    if (!isExistingChunk(plantLocation)) {
                        continue;
                    }

                    Block testBlock = block.getWorld().getBlockAt(plantLocation);

                    if (!this.config.growBiomes.contains(testBlock.getBiome())) {
                        continue;
                    }

                    int fuzzyEdges = random.nextInt(clusterDiameter);

                    if (fuzzyEdges > highX - 2 || fuzzyEdges < lowX + 2 || fuzzyEdges > highZ - 2
                            || fuzzyEdges < lowZ + 2) {
                        if (random.nextBoolean()) {
                            continue;
                        }
                    }

                    Block plantBlock = findHighestSolidBlock(testBlock.getLocation(), 255);

                    if (!this.config.growBlocks.contains(plantBlock.getType().getKey())) {
                        continue;
                    }

                    if (blockType == Material.KELP_PLANT) {
                        plantKelp(random, plantBlock);
                    } else if (blockType == Material.SEAGRASS) {
                        plantSeagrass(random, plantBlock);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void plantKelp(Random random, Block plantBlock) {
        Block upBlock = plantBlock.getRelative(BlockFace.UP);

        if (!isWater(upBlock)) {
            return;
        }

        int height = random.nextInt(10) + 1;
        int top = upBlock.getY() + height;

        while (upBlock.getY() <= top) {
            if (upBlock.getY() == top || !isWater(upBlock.getRelative(BlockFace.UP))) {
                upBlock.setType(Material.KELP);
                Ageable kelp = (Ageable) upBlock.getBlockData();
                kelp.setAge(random.nextInt(23));
                upBlock.setBlockData(kelp);
                break;
            } else {
                upBlock.setType(Material.KELP_PLANT);
            }

            upBlock = upBlock.getRelative(BlockFace.UP);
        }
    }

    public void plantSeagrass(Random random, Block plantBlock) {
        Block upBlock = plantBlock.getRelative(BlockFace.UP);
        if (upBlock.getWorld().getBlockAt(upBlock.getX(), upBlock.getY() + 1, upBlock.getZ())
                .getType() != Material.WATER) {
            return;
        }

        if (random.nextInt(3) == 0) {
            upBlock.setType(Material.TALL_SEAGRASS);
        } else {
            upBlock.setType(Material.SEAGRASS);
        }
    }

    public Block findHighestSolidBlock(Location location, int start) {
        if (start > 255) {
            start = 255;
        }

        World world = location.getWorld();

        for (; start > 0; --start) {
            Block block = world.getBlockAt(location.getBlockX(), start, location.getBlockZ());

            if (!block.isEmpty() && !block.isLiquid()) {
                return block;
            }
        }

        return world.getBlockAt(location.getBlockX(), 0, location.getBlockZ());
    }

    private boolean isExistingChunk(Location location) {
        return location.getWorld().loadChunk(location.getBlockX() >> 4, location.getBlockZ() >> 4, false);
    }

    private boolean isWater(Block block) {
        return block.getType() == Material.WATER;
    }
}
