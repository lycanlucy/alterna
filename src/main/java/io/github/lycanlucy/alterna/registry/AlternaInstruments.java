package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Instrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaInstruments {
    public static final DeferredRegister<Instrument> INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, Alterna.MOD_ID);

    public static final DeferredHolder<Instrument, Instrument> POUR_CONCH_SHELL = conchShell("pour_conch_shell", AlternaSounds.CONCH_SHELL_PLAY_POUR);
    public static final DeferredHolder<Instrument, Instrument> ROAR_CONCH_SHELL = conchShell("roar_conch_shell", AlternaSounds.CONCH_SHELL_PLAY_ROAR);
    public static final DeferredHolder<Instrument, Instrument> SHINE_CONCH_SHELL = conchShell("shine_conch_shell", AlternaSounds.CONCH_SHELL_PLAY_SHINE);

    public static DeferredHolder<Instrument, Instrument> conchShell(String name, Holder<SoundEvent> sound) {
        return INSTRUMENTS.register(name, () -> new Instrument(sound, 140, 256.0f));
    }

    public static void register(IEventBus eventBus) {
        INSTRUMENTS.register(eventBus);
    }
}
