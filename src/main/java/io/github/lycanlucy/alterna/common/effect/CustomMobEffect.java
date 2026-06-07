package io.github.lycanlucy.alterna.common.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.function.Function;
import java.util.function.Supplier;

public class CustomMobEffect extends MobEffect {
    private final Supplier<ParticleType<SimpleParticleType>> particleOptions;

    public CustomMobEffect(MobEffectCategory category, int color, Supplier<ParticleType<SimpleParticleType>> particle) {
        super(category, color);
        this.particleOptions = particle;
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        Function<MobEffectInstance, ParticleOptions> particleFactory = instance -> (ParticleOptions) this.particleOptions.get();
        return particleFactory.apply(effect);
    }
}
