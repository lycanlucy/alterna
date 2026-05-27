package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<SimpleParticleType>> LORD_OF_THE_SKIES = PARTICLES.register("lord_of_the_skies", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
