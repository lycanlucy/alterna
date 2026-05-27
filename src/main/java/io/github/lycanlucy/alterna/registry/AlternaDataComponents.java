package io.github.lycanlucy.alterna.registry;

import com.mojang.serialization.Codec;
import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> PROJECTILE_BASE_DAMAGE = DATA_COMPONENTS.registerComponentType("projectile_base_damage", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
