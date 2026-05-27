package io.github.lycanlucy.alterna.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.util.SpecialMobEffect;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public class GuiMixin {
    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
    private ResourceLocation renderSpecialEffectBackground(ResourceLocation sprite, @Local MobEffectInstance effectInstance) {
        if (SpecialMobEffect.SPECIAL_MOB_EFFECTS.contains(effectInstance.getEffect())) {
            return SpecialMobEffect.GUI_BACKGROUND_SPRITE;
        }
        return sprite;
    }
}
