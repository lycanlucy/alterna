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
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCH_SHELL_PLAY_POUR = sound("item.conch_shell.play.pour");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCH_SHELL_PLAY_ROAR = sound("item.conch_shell.play.roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCH_SHELL_PLAY_SHINE = sound("item.conch_shell.play.shine");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_RACK_ADD_ITEM = sound("block.item_rack.add_item");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_RACK_REMOVE_ITEM = sound("block.item_rack.remove_item");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_RACK_ROTATE_ITEM = sound("block.item_rack.rotate_item");

    public static DeferredHolder<SoundEvent, SoundEvent> sound(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Alterna.modId(name)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
