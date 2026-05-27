package io.github.lycanlucy.alterna.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.util.ModifyEffectsBiomeModifier;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AlternaBiomeModifierSerializers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Alterna.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModifyEffectsBiomeModifier>> MODIFY_EFFECTS = BIOME_MODIFIERS.register("modify_effects", () ->
            RecordCodecBuilder.mapCodec(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModifyEffectsBiomeModifier::biomes),
                    Codec.INT.optionalFieldOf("grass_color").forGetter(ModifyEffectsBiomeModifier::grassColor),
                    Codec.INT.optionalFieldOf("foliage_color").forGetter(ModifyEffectsBiomeModifier::foliageColor),
                    Codec.INT.optionalFieldOf("water_color").forGetter(ModifyEffectsBiomeModifier::waterColor),
                    Codec.INT.optionalFieldOf("water_fog_color").forGetter(ModifyEffectsBiomeModifier::waterFogColor)
            ).apply(builder, ModifyEffectsBiomeModifier::new)));

    public static void register(IEventBus eventBus) {
        BIOME_MODIFIERS.register(eventBus);
    }
}
