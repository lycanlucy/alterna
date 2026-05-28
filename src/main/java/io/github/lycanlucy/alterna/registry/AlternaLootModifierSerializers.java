package io.github.lycanlucy.alterna.registry;

import com.mojang.serialization.MapCodec;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.loot.RemoveItemLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AlternaLootModifierSerializers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Alterna.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<RemoveItemLootModifier>> REMOVE_ITEM = LOOT_MODIFIERS.register("remove_item", () -> RemoveItemLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
