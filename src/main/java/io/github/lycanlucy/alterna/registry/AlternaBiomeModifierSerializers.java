package io.github.lycanlucy.alterna.registry;

import com.mojang.serialization.MapCodec;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.biome.ModifyEffectsBiomeModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AlternaBiomeModifierSerializers {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Alterna.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModifyEffectsBiomeModifier>> MODIFY_EFFECTS = BIOME_MODIFIERS.register("modify_effects", () -> ModifyEffectsBiomeModifier.CODEC);

    public static void register(IEventBus eventBus) {
        BIOME_MODIFIERS.register(eventBus);
    }
}
