package io.github.lycanlucy.alterna.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AuraParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private final double xStart;
    private final double zStart;

    protected AuraParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites) {
        super(level, x, y, z);
        this.xStart = this.x;
        this.zStart = this.z;
        this.x = xStart + Mth.cos(this.age * 0.12f);
        this.z = zStart + Mth.sin(this.age * 0.12f);
        this.xo = this.x;
        this.zo = this.z;
        this.sprites = sprites;
        this.gravity = 0.0F;
        this.quadSize *= 1.0F;
        this.lifetime = (int) (Math.random() * 10.0) + 20;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(sprites);
            this.x = xStart + Mth.cos(this.age * 0.12f);
            this.z = zStart + Mth.sin(this.age * 0.12f);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new AuraParticle(level, x, y, z, this.sprite);
        }
    }
}
