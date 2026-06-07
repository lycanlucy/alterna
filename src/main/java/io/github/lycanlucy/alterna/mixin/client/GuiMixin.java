package io.github.lycanlucy.alterna.mixin.client;


import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.tag.AlternaMobEffectTags;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public class GuiMixin {
    @Unique
    private static final ResourceLocation alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE = Alterna.id("hud/effect_background_special");

    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 0)
    private ResourceLocation renderSpecialEffectBackground(ResourceLocation sprite, @Local MobEffectInstance effectInstance) {
        if (effectInstance.getEffect().is(AlternaMobEffectTags.UNCLEARABLE)) {
            return alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE;
        }
        return sprite;
    }
}