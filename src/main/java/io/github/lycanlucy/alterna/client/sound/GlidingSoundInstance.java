package io.github.lycanlucy.alterna.client.sound;

import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class GlidingSoundInstance extends AbstractTickableSoundInstance {
    public static final int DELAY = 20;
    private final LocalPlayer player;
    private int time;

    public GlidingSoundInstance(LocalPlayer player) {
        super(AlternaSounds.GLIDER_GLIDE.get(), SoundSource.PLAYERS, SoundInstance.createUnseededRandom());
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0.1F;
    }

    @Override
    public void tick() {
        this.time++;
        if (!this.player.isRemoved() && (this.time <= DELAY || GliderItem.isGliding(player))) {
            this.x = (float) this.player.getX();
            this.y = (float) this.player.getY();
            this.z = (float) this.player.getZ();
            float f = (float) this.player.getDeltaMovement().lengthSqr() * 20;
            if ((double) f >= 1.0E-7) {
                this.volume = Mth.clamp(f / 4.0F, 0.0F, 1.0F);
            } else {
                this.volume = 0.0F;
            }

            if (this.time < DELAY) {
                this.volume = 0.0F;
            } else if (this.time < 40) {
                this.volume = this.volume * ((float) (this.time - DELAY) / DELAY);
            }

            if (this.volume > 0.8F) {
                this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
                this.pitch = 1.0F;
            }
        } else {
            this.stop();
        }
    }
}