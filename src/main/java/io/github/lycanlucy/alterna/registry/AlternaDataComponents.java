package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.TridentProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<TridentProperties>> TRIDENT_PROPERTIES = DATA_COMPONENTS.registerComponentType("trident_properties", builder -> builder.persistent(TridentProperties.CODEC).networkSynchronized(TridentProperties.STREAM_CODEC));

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
