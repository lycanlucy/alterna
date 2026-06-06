package io.github.lycanlucy.alterna.client;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.List;

public class AlternaClientColors {
    public static final int DEFAULT_WATER_COLOR = 4501493;
    public static final Object2IntArrayMap<Biome> BIOME_WATER_COLORS = new Object2IntArrayMap<>();
    public static final Object2IntArrayMap<Biome> BIOME_WATER_FOG_COLORS = new Object2IntArrayMap<>();
    public static final ColorResolver WATER_COLOR_RESOLVER = (biome, x, z) -> BIOME_WATER_COLORS.containsKey(biome) ? BIOME_WATER_COLORS.getInt(biome) : biome.getWaterColor();

    public static int salmonSpawnEgg(int tintIndex) {
        return tintIndex == 0 ? 0xa3a6ba : 0x91962a;
    }

    public static void initializeBiomeColors(LevelAccessor level) {
        Registry<Biome> biomes = level.registryAccess().registryOrThrow(Registries.BIOME);
        registerDefaultWaterColor(biomes, List.of(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.SNOWY_SLOPES, Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS, Biomes.STONY_PEAKS, Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES, Biomes.DEEP_DARK, Biomes.MEADOW, Biomes.GROVE));
        registerWaterColor(biomes, Biomes.SNOWY_PLAINS, 1332635);
        registerWaterColor(biomes, Biomes.ICE_SPIKES, 1332635);
        registerWaterColor(biomes, Biomes.DESERT, 3319192);
        registerWaterColor(biomes, Biomes.FOREST, 2004978);
        registerWaterColor(biomes, Biomes.FLOWER_FOREST, 2139084);
        registerWaterColor(biomes, Biomes.BIRCH_FOREST, 423886);
        registerWaterColor(biomes, Biomes.DARK_FOREST, 3894481);
        registerWaterColor(biomes, Biomes.OLD_GROWTH_PINE_TAIGA, 2977143);
        registerWaterColor(biomes, Biomes.TAIGA, 2650242);
        registerWaterColor(biomes, Biomes.SNOWY_TAIGA, 2121347);
        registerWaterColor(biomes, Biomes.SAVANNA, 2919324);
        registerWaterColor(biomes, Biomes.SAVANNA_PLATEAU, 2461864);
        registerWaterColor(biomes, Biomes.WINDSWEPT_HILLS, 31735);
        registerWaterColor(biomes, Biomes.WINDSWEPT_GRAVELLY_HILLS, 943019);
        registerWaterColor(biomes, Biomes.WINDSWEPT_FOREST, 943019);
        registerWaterColor(biomes, Biomes.WINDSWEPT_SAVANNA, 2461864);
        registerWaterColor(biomes, Biomes.JUNGLE, 1352389);
        registerWaterColor(biomes, Biomes.SPARSE_JUNGLE, 887523);
        registerWaterColor(biomes, Biomes.BAMBOO_JUNGLE, 1352389);
        registerWaterColor(biomes, Biomes.BADLANDS, 5144449);
        registerWaterColor(biomes, Biomes.ERODED_BADLANDS, 4816793);
        registerWaterColor(biomes, Biomes.WOODED_BADLANDS, 5603486);
        registerWaterColor(biomes, Biomes.RIVER, 34047);
        registerWaterColor(biomes, Biomes.FROZEN_RIVER, 1594256);
        registerWaterColor(biomes, Biomes.BEACH, 1408171);
        registerWaterColor(biomes, Biomes.SNOWY_BEACH, 1336229);
        registerWaterColor(biomes, Biomes.STONY_SHORE, 878523);
        registerWaterColor(biomes, Biomes.WARM_OCEAN, 176357);
        registerWaterColor(biomes, Biomes.LUKEWARM_OCEAN, 890587);
        registerWaterColor(biomes, Biomes.DEEP_LUKEWARM_OCEAN, 890587);
        registerWaterColor(biomes, Biomes.OCEAN, 1542100);
        registerWaterColor(biomes, Biomes.DEEP_OCEAN, 1542100);
        registerWaterColor(biomes, Biomes.COLD_OCEAN, 2130121);
        registerWaterColor(biomes, Biomes.DEEP_COLD_OCEAN, 2130121);
        registerWaterColor(biomes, Biomes.FROZEN_OCEAN, 2453685);
        registerWaterColor(biomes, Biomes.DEEP_FROZEN_OCEAN, 2453685);
        registerWaterColor(biomes, Biomes.MUSHROOM_FIELDS, 9079191);

        registerWaterFogColor(biomes, Biomes.WARM_OCEAN, 166357);
        registerWaterFogColor(biomes, Biomes.LUKEWARM_OCEAN, 685252);
        registerWaterFogColor(biomes, Biomes.DEEP_LUKEWARM_OCEAN, 946873);
        registerWaterFogColor(biomes, Biomes.OCEAN, 1140144);
        registerWaterFogColor(biomes, Biomes.DEEP_OCEAN, 1336229);
        registerWaterFogColor(biomes, Biomes.COLD_OCEAN, 1332635);
        registerWaterFogColor(biomes, Biomes.DEEP_COLD_OCEAN, 1594256);
        registerWaterFogColor(biomes, Biomes.FROZEN_OCEAN, 1526149);
        registerWaterFogColor(biomes, Biomes.DEEP_FROZEN_OCEAN, 1722489);
    }

    private static void registerWaterColor(Registry<Biome> registry, ResourceKey<Biome> biome, int color) {
        BIOME_WATER_COLORS.put(registry.get(biome), color);
    }

    private static void registerDefaultWaterColor(Registry<Biome> registry, List<ResourceKey<Biome>> biomes) {
        for (ResourceKey<Biome> biome : biomes) {
            BIOME_WATER_COLORS.put(registry.get(biome), DEFAULT_WATER_COLOR);
        }
    }

    private static void registerWaterFogColor(Registry<Biome> registry, ResourceKey<Biome> biome, int color) {
        BIOME_WATER_FOG_COLORS.put(registry.get(biome), color);
    }
}
