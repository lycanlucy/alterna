package io.github.lycanlucy.alterna.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.tag.AlternaMobEffectTags;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EffectRenderingInventoryScreen.class)
public class EffectRenderingInventoryScreenMixin {
    @Unique
    private static final ResourceLocation alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE_SMALL = Alterna.id("container/inventory/effect_background_special_small");
    @Unique
    private static final ResourceLocation alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE_LARGE = Alterna.id("container/inventory/effect_background_special_large");

    @ModifyArg(method = "renderBackgrounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0), index = 0)
    private ResourceLocation renderSpecialEffectBackgroundLarge(ResourceLocation sprite, @Local MobEffectInstance effectInstance) {
        if (effectInstance.getEffect().is(AlternaMobEffectTags.UNCLEARABLE)) {
            return alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE_LARGE;
        }
        return sprite;
    }

    @ModifyArg(method = "renderBackgrounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1), index = 0)
    private ResourceLocation renderSpecialEffectBackgroundSmall(ResourceLocation sprite, @Local MobEffectInstance effectInstance) {
        if (effectInstance.getEffect().is(AlternaMobEffectTags.UNCLEARABLE)) {
            return alterna$UNCLEARABLE_EFFECT_BACKGROUND_SPRITE_SMALL;
        }
        return sprite;
    }
}