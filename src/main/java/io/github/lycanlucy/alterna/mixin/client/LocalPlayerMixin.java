package io.github.lycanlucy.alterna.mixin.client;

import com.mojang.authlib.GameProfile;
import io.github.lycanlucy.alterna.client.sound.GlidingSoundInstance;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }

    @Inject(method = "isHandsBusy", at = @At("HEAD"), cancellable = true)
    private void setHandsBusyWhenGliding(CallbackInfoReturnable<Boolean> cir) {
        if (GliderItem.isGliding(this)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (GliderItem.shouldGlide(this) && !GliderItem.isGliding(this)) {
            Minecraft.getInstance().getSoundManager().play(new GlidingSoundInstance((LocalPlayer) (Object) this));
        }
    }
}