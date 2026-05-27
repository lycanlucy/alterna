package io.github.lycanlucy.alterna.common.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class LordOfTheSkiesMobEffect extends MobEffect {
    private final Supplier<ParticleType<SimpleParticleType>> particleOptions;

    public LordOfTheSkiesMobEffect(MobEffectCategory category, int color, Supplier<ParticleType<SimpleParticleType>> particle) {
        super(category, color);
        this.particleOptions = particle;
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(@NotNull MobEffectInstance effect) {
        Function<MobEffectInstance, ParticleOptions> particleFactory = effectInstance -> (ParticleOptions) this.particleOptions.get();
        return particleFactory.apply(effect);
    }
}
