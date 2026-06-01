package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.effect.LordOfTheSkiesMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;

public class AlternaMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Alterna.MOD_ID);
    public static final HashMap<Supplier<? extends MobEffect>, String> TRANSLATIONS = new HashMap<>();

    public static final DeferredHolder<MobEffect, MobEffect> LORD_OF_THE_SKIES = MOB_EFFECTS.register("lord_of_the_skies", () -> new LordOfTheSkiesMobEffect(MobEffectCategory.BENEFICIAL, 0x919191, AlternaParticles.LORD_OF_THE_SKIES).withSoundOnAdded(AlternaSounds.APPLY_EFFECT_LORD_OF_THE_SKIES.get()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
        TRANSLATIONS.put(LORD_OF_THE_SKIES, "Lord of the Skies");
    }
}
