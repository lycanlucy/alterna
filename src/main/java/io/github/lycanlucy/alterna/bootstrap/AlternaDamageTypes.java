package io.github.lycanlucy.alterna.bootstrap;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;

public class AlternaDamageTypes {
    public static final ResourceKey<DamageType> INK_SAC = key("ink_sac");

    public static DamageSource inkSac(Entity attacker) {
        return new DamageSource(attacker.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(INK_SAC), attacker);
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(INK_SAC, new DamageType("alterna.ink_sac", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F, DamageEffects.HURT, DeathMessageType.DEFAULT));
    }

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, Alterna.id(name));
    }
}