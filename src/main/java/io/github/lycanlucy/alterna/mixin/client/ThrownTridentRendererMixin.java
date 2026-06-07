package io.github.lycanlucy.alterna.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownTridentRenderer.class)
public abstract class ThrownTridentRendererMixin extends EntityRenderer<ThrownTrident> {
    protected ThrownTridentRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/projectile/ThrownTrident;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void renderAsItem(ThrownTrident entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (AlternaClientConfig.redesignTrident() || entity.getData(AlternaAttachments.ORIGINAL_STACK).is(AlternaItems.SUNKEN_TRIDENT)) {
            poseStack.pushPose();
            float yRot = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F;
            float zRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 225.0F;

            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            poseStack.translate(-0.3, 0.2, 0.0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(zRot));
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getData(AlternaAttachments.ORIGINAL_STACK), ItemDisplayContext.NONE, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
            ci.cancel();
        }
    }
}
