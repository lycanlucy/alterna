package io.github.lycanlucy.alterna.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LiquidBlockRenderer.class)
public class LiquidBlockRendererMixin {
    @Shadow
    private TextureAtlasSprite waterOverlay;

    @ModifyArg(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V", ordinal = 12), index = 7)
    private float aquariumOpacity1(float alpha, @Local TextureAtlasSprite textureAtlasSprite) {
        if (textureAtlasSprite == this.waterOverlay) {
            return (float) AlternaClientConfig.aquariumOpacity();
        }
        return alpha;
    }

    @ModifyArg(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V", ordinal = 13), index = 7)
    private float aquariumOpacity2(float alpha, @Local TextureAtlasSprite textureAtlasSprite) {
        if (textureAtlasSprite == this.waterOverlay) {
            return (float) AlternaClientConfig.aquariumOpacity();
        }
        return alpha;
    }

    @ModifyArg(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V", ordinal = 14), index = 7)
    private float aquariumOpacity3(float alpha, @Local TextureAtlasSprite textureAtlasSprite) {
        if (textureAtlasSprite == this.waterOverlay) {
            return (float) AlternaClientConfig.aquariumOpacity();
        }
        return alpha;
    }

    @ModifyArg(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V", ordinal = 15), index = 7)
    private float aquariumOpacity4(float alpha, @Local TextureAtlasSprite textureAtlasSprite) {
        if (textureAtlasSprite == this.waterOverlay) {
            return (float) AlternaClientConfig.aquariumOpacity();
        }
        return alpha;
    }
}