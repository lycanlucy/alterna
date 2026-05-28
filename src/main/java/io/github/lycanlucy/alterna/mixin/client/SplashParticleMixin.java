package io.github.lycanlucy.alterna.mixin.client;

import io.github.lycanlucy.alterna.util.ColoredParticleHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashParticle.class)
public abstract class SplashParticleMixin extends TextureSheetParticle {
    protected SplashParticleMixin(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void matchColorToBiome(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, CallbackInfo ci) {
        Vector3f color = ColoredParticleHelper.getWaterColor(level, x, y, z);
        this.setColor(color.x, color.y, color.z);
    }
}
