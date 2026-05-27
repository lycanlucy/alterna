package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.AlternaThrownTrident;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<AlternaThrownTrident>> TRIDENT = ENTITIES.register("trident", () -> EntityType.Builder.<AlternaThrownTrident>of(AlternaThrownTrident::new, MobCategory.MISC).sized(0.5F, 0.5F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20).build("trident"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
