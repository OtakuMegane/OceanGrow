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

    private final Configuration config;

    public Grow(OceanGrow instance) {
        this.config = new Configuration(instance);
    }

    public void growFunction(World world, Block block, Material blockType) {
        Location blockLocation = block.getLocation();
        int x = blockLocation.getBlockX();
        int z = blockLocation.getBlockZ();
        int growRadius = config.growRadius;
        Random random = new Random(world.getSeed() + x + z);

        for (int x1 = 0 - growRadius; x1 <= growRadius; x1++) {
            for (int z1 = 0 - growRadius; z1 <= growRadius; z1++) {
                int clusterDensity = 0;
                int clusterRadius = 0;

                Block centerBlock = block.getWorld().getBlockAt(x + x1, 0, z + z1);
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

                    Block block2 = block.getWorld().getBlockAt(lowX + xRand, 0, lowZ + zRand);

                    if (block2.getBiome() != Biome.OCEAN && block2.getBiome() != Biome.DEEP_OCEAN) {
                        continue;
                    }

                    int limiter = random.nextInt(clusterDiameter);

                    if(limiter > highX - 2 || limiter < lowX + 2 || limiter > highZ - 2 || limiter < lowZ + 2)
                    {
                        if(random.nextBoolean())
                        {
                            continue;
                        }
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

    public void plantKelp(Random random, Block topSolid) {
        Block upBlock = topSolid.getRelative(BlockFace.UP);
        int height = random.nextInt(10) + 1;
        int top = upBlock.getY() + height;

        while (upBlock.getY() <= top) {
            if (upBlock.getY() == top || upBlock.getRelative(BlockFace.UP).getType() != Material.WATER) {
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
