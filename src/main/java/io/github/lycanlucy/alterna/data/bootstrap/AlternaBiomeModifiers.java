package io.github.lycanlucy.alterna.data.bootstrap;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.util.ModifyEffectsBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class AlternaBiomeModifiers {
    public static final int DEFAULT_WATER_COLOR = 4501493;

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Alterna.modId(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        changeBiomeColors(context, biomes);
    }

    private static void changeBiomeColors(BootstrapContext<BiomeModifier> context, HolderGetter<Biome> biomes) {
        changeWater(context, biomes, Biomes.PLAINS, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.SUNFLOWER_PLAINS, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.SNOWY_PLAINS, 1332635);
        changeWater(context, biomes, Biomes.ICE_SPIKES, 1332635);
        changeWater(context, biomes, Biomes.DESERT, 3319192);
        changeWater(context, biomes, Biomes.FOREST, 2004978);
        changeWater(context, biomes, Biomes.FLOWER_FOREST, 2139084);
        changeWater(context, biomes, Biomes.BIRCH_FOREST, 423886);
        changeWater(context, biomes, Biomes.DARK_FOREST, 3894481);
        changeWater(context, biomes, Biomes.OLD_GROWTH_BIRCH_FOREST, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.OLD_GROWTH_PINE_TAIGA, 2977143);
        changeWater(context, biomes, Biomes.OLD_GROWTH_SPRUCE_TAIGA, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.TAIGA, 2650242);
        changeWater(context, biomes, Biomes.SNOWY_TAIGA, 2121347);
        changeWater(context, biomes, Biomes.SAVANNA, 2919324);
        changeWater(context, biomes, Biomes.SAVANNA_PLATEAU, 2461864);
        changeWater(context, biomes, Biomes.WINDSWEPT_HILLS, 31735);
        changeWater(context, biomes, Biomes.WINDSWEPT_GRAVELLY_HILLS, 943019);
        changeWater(context, biomes, Biomes.WINDSWEPT_FOREST, 943019);
        changeWater(context, biomes, Biomes.WINDSWEPT_SAVANNA, 2461864);
        changeWater(context, biomes, Biomes.JUNGLE, 1352389);
        changeWater(context, biomes, Biomes.SPARSE_JUNGLE, 887523);
        changeWater(context, biomes, Biomes.BAMBOO_JUNGLE, 1352389);
        changeWater(context, biomes, Biomes.BADLANDS, 5144449);
        changeWater(context, biomes, Biomes.ERODED_BADLANDS, 4816793);
        changeWater(context, biomes, Biomes.WOODED_BADLANDS, 5603486);
        changeWater(context, biomes, Biomes.MEADOW, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.GROVE, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.SNOWY_SLOPES, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.FROZEN_PEAKS, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.JAGGED_PEAKS, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.STONY_PEAKS, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.RIVER, 34047);
        changeWater(context, biomes, Biomes.FROZEN_RIVER, 1594256);
        changeWater(context, biomes, Biomes.BEACH, 1408171);
        changeWater(context, biomes, Biomes.SNOWY_BEACH, 1336229);
        changeWater(context, biomes, Biomes.STONY_SHORE, 878523);
        changeWater(context, biomes, Biomes.WARM_OCEAN, 176357, 166357);
        changeWater(context, biomes, Biomes.LUKEWARM_OCEAN, 890587, 685252);
        changeWater(context, biomes, Biomes.DEEP_LUKEWARM_OCEAN, 890587, 946873);
        changeWater(context, biomes, Biomes.OCEAN, 1542100, 1140144);
        changeWater(context, biomes, Biomes.DEEP_OCEAN, 1542100, 1336229);
        changeWater(context, biomes, Biomes.COLD_OCEAN, 2130121, 1332635);
        changeWater(context, biomes, Biomes.DEEP_COLD_OCEAN, 2130121, 1594256);
        changeWater(context, biomes, Biomes.FROZEN_OCEAN, 2453685, 1526149);
        changeWater(context, biomes, Biomes.DEEP_FROZEN_OCEAN, 2453685, 1722489);
        changeWater(context, biomes, Biomes.MUSHROOM_FIELDS, 9079191);
        changeWater(context, biomes, Biomes.DRIPSTONE_CAVES, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.LUSH_CAVES, DEFAULT_WATER_COLOR);
        changeWater(context, biomes, Biomes.DEEP_DARK, DEFAULT_WATER_COLOR);
    }

    private static void changeWater(BootstrapContext<BiomeModifier> context, HolderGetter<Biome> biomes, ResourceKey<Biome> target, int waterColor, int waterFogColor) {
        context.register(key("modify_" + target.location().getPath() + "_effects"), new ModifyEffectsBiomeModifier(HolderSet.direct(biomes.getOrThrow(target)), Optional.empty(), Optional.empty(), Optional.of(waterColor), Optional.of(waterFogColor)));
    }

    private static void changeWater(BootstrapContext<BiomeModifier> context, HolderGetter<Biome> biomes, ResourceKey<Biome> target, int waterColor) {
        changeWater(context, biomes, target, waterColor, waterColor);
    }
}
