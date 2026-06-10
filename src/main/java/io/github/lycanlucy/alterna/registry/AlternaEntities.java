package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.Octopus;
import io.github.lycanlucy.alterna.common.entity.VanishItemFrame;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<VanishItemFrame>> VANISH_ITEM_FRAME = ENTITIES.register(
            "vanish_item_frame", () -> EntityType.Builder.<VanishItemFrame>of(VanishItemFrame::new, MobCategory.MISC).sized(0.5F, 0.5F)
                    .eyeHeight(0.0F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build("vanish_item_frame")
    );
    public static final DeferredHolder<EntityType<?>, EntityType<Octopus>> OCTOPUS = ENTITIES.register(
            "octopus", () -> EntityType.Builder.of(Octopus::new, MobCategory.WATER_CREATURE).sized(0.85F, 0.85F)
                    .clientTrackingRange(10)
                    .build("octops")
    );

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
