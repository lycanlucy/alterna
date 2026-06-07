package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.advancement.BoostedWithGliderTrigger;
import io.github.lycanlucy.alterna.common.advancement.SpawnedPhantomsTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, BoostedWithGliderTrigger> BOOSTED_WITH_GLIDER = TRIGGERS.register("boosted_with_glider", BoostedWithGliderTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SpawnedPhantomsTrigger> SPAWNED_PHANTOMS = TRIGGERS.register("spawned_phantoms", SpawnedPhantomsTrigger::new);

    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }
}
