package io.github.lycanlucy.alterna.bootstrap;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class AlternaBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_SPAWN_OCTOPUS = key("add_spawn_octopus");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        context.register(ADD_SPAWN_OCTOPUS, new BiomeModifiers.AddSpawnsBiomeModifier(
                context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OCEAN),
                List.of(new MobSpawnSettings.SpawnerData(AlternaEntities.OCTOPUS.get(), 3, 1, 2))
        ));
    }

    private static ResourceKey<BiomeModifier> key(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Alterna.id(name));
    }
}
