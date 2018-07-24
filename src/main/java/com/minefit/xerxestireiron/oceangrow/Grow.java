package com.minefit.xerxestireiron.oceangrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Grow {

    private final Configuration config;

    public Grow(OceanGrow instance) {
        this.config = new Configuration(instance);
    }

    public void growFunction(World world, Block block, Material blockType) {
        Location bLocation = block.getLocation();
        int x = (int) bLocation.getX();
        int z = (int) bLocation.getZ();
        int growRadius = config.getRadius("grow");
        Random random = new Random(world.getSeed() + x + z);

        for (int x1 = 0 - growRadius; x1 <= growRadius; x1++) {
            for (int z1 = 0 - growRadius; z1 <= growRadius; z1++) {
                int clusterDensity = 0;
                Block centerBlock = block.getWorld().getBlockAt(x + x1, 0, z + z1);

                if (blockType == Material.KELP_PLANT) {
                    if (random.nextInt(1000) >= config.clusterFactorKelp) {
                        continue;
                    }

                    clusterDensity = random.nextInt(config.getDensity("kelp")) + 1;
                } else if (blockType == Material.SEAGRASS) {
                    if (random.nextInt(1000) >= config.clusterFactorSeagrass) {
                        continue;
                    }

                    clusterDensity = random.nextInt(config.getDensity("seagrass")) + 1;
                }

                int clusterRadiusMin = config.getRadius("cluster") / 2;
                int clusterRadius = random.nextInt(config.getRadius("cluster")) + clusterRadiusMin;
                int radiusStart = 0 - clusterRadius;
                int radiusEnd = clusterRadius;

                for (int x2 = radiusStart; x2 <= radiusEnd; x2++) {
                    for (int z2 = radiusStart; z2 <= radiusEnd; z2++) {
                        Block block2 = block.getWorld().getBlockAt(centerBlock.getX() + x2, 0, centerBlock.getZ() + z2);

                        if (clusterRadius > 5) {
                            if (x2 < radiusStart + 2 || x2 < radiusStart + 2 || x2 > radiusEnd - 2
                                    || x2 > radiusEnd - 2) {
                                if (random.nextBoolean()) {
                                    continue;
                                }

                            }

                            if (z2 < radiusStart + 2 || z2 < radiusStart + 2 || z2 > radiusEnd - 2
                                    || z2 > radiusEnd - 2) {
                                if (random.nextBoolean()) {
                                    continue;
                                }
                            }

                        }

                        if (random.nextInt(100) >= clusterDensity
                                || (block2.getBiome() != Biome.OCEAN && block2.getBiome() != Biome.DEEP_OCEAN)) {
                            continue;
                        }

                        Block topSolid = findHighestSolidBlock(block2.getLocation(), 255);

                        if (topSolid.getType() != Material.SAND && topSolid.getType() != Material.GRAVEL
                                && topSolid.getType() != Material.DIRT) {
                            continue;
                        }

                        if (blockType == Material.KELP_PLANT) {
                            plantKelp(random, topSolid);
                        } else if (blockType == Material.SEAGRASS) {
                            plantSeagrass(random, topSolid);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    public void plantKelp(Random random, Block topSolid) {
        Block upBlock = topSolid.getRelative(BlockFace.UP);
        Block topWater = topSolid.getWorld().getHighestBlockAt(topSolid.getLocation());
        int heightRange = topWater.getY() - topSolid.getY();

        if (heightRange < 2) {
            return;
        }

        int heightLimit = topSolid.getY() + (random.nextInt(heightRange) - 1);

        while (upBlock.getY() <= heightLimit && upBlock.getType() == Material.WATER) {
            upBlock.setType(Material.KELP_PLANT);
            upBlock = upBlock.getRelative(BlockFace.UP);
        }
    }

    public void plantSeagrass(Random random, Block topSolid) {
        Block upBlock = topSolid.getRelative(BlockFace.UP);
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
}
