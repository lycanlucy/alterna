package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Alterna.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> APPLY_EFFECT_LORD_OF_THE_SKIES = sound("event.mob_effect.lord_of_the_skies");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUCKET_EMPTY_BABY_TURTLE = sound("item.bucket.empty_baby_turtle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUCKET_FILL_BABY_TURTLE = sound("item.bucket.fill_baby_turtle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLIDER_BOOST = sound("item.glider.boost");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLIDER_CLOSE = sound("item.glider.close");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLIDER_GLIDE = sound("item.glider.glide");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLIDER_OPEN = sound("item.glider.open");
    public static final DeferredHolder<SoundEvent, SoundEvent> GLOW_INK_SAC_SPRAY = sound("item.glow_ink_sac.spray");
    public static final DeferredHolder<SoundEvent, SoundEvent> INK_SAC_SPRAY = sound("item.ink_sac.spray");

    public static DeferredHolder<SoundEvent, SoundEvent> sound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Alterna.id(name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
