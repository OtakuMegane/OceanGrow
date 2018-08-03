# OceanGrow

A plugin making it easier to grow kelp and seagrass in pre-1.13 oceans.

## Installation
 - Put the OceanGrow .jar in the `plugins` directory.
 - On first load it will place a default `config.yml` in `plugis/OceanGrow`. Edit this if you like.
 
## Usage
 - To get a wand for growing kelp: `/oceangrow kelp-wand`

 - To get a wand for growing seagrass: `/oceangrow seagrass-wand`

 - To reload the configuration from the current config.yml: `/oceangrow reload`
 
Basically works like bonemeal. Place the wand in your main hand and click somewhere to grow kelp or seagrass. The wand does not have to be used in an ocean biome; using it on land will grow plants in ocean blocks if there are any within part of the radius but will do nothing with the land blocks. The wand can be used at any random location or in a planned grid to neatly fill in large areas.

NOTE: Due to the nature of random numbers, in rare cases you may get little or no kelp or seagrass when using the wand on a given block. If this happens, clicking on an adjacent block will generate a different pattern of plants and fill in the area.

## Permissions
The permissions available for OceanGrow:
 - `oceangrow.usewand`: Allows use of the wands. Defaults to OP-only.
 - `oceangrow.reload`: Allows user to reload the configuration from the `config.yml` file. Defaults to OP-only.

## Configuration
The default config.yml, with notes on what each setting does:

```
# The default configuration is set to emulate the vanilla plant distribution.
# You can experiment with the settings if you wish of course.

# The radius in blocks to consider for cluster centers; the clusters themselves may extend outside this radius.
# Anything beyond 100 will begin to lag most servers and going high enough risks freezing or a crash.
grow-radius: 20

# A probability modifier used when determining if a given spot inside the grow_radius will be chosen as a cluster center.
cluster-factor:
  kelp: 3
  seagrass: 15

# The maximum radius for clusters in blocks. Each cluster will be a random size up to this value.
cluster-radius:
  kelp: 8
  seagrass: 4

# Average density of each cluster. Range 0-100.
cluster-density:
  kelp: 35
  seagrass: 60

# A list of blocks that kelp and seagrass can grow on
# Must be a string in standard Minecraft ID format
grow-blocks:
  - "minecraft:sand"
  - "minecraft:gravel"
  - "minecraft:dirt"
  - "minecraft:clay"

# A list of biomes that kelp and seagrass will grow in
# Must be a string in standard Minecraft ID format
grow-biomes:
  - "minecraft:ocean"
  - "minecraft:deep_ocean"
```
