package io.github.lycanlucy.alterna.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.common.entity.AlternaThrownTrident;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class AlternaThrownTridentRenderer extends EntityRenderer<AlternaThrownTrident> {
    private final ItemRenderer itemRenderer;

    public AlternaThrownTridentRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(AlternaThrownTrident thrownTrident, float entityYaw, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float yRot = Mth.lerp(partialTick, thrownTrident.yRotO, thrownTrident.getYRot()) - 90.0F;
        float zRot = Mth.lerp(partialTick, thrownTrident.xRotO, thrownTrident.getXRot()) + 225.0F;

        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.translate(-0.3, 0.2, 0.0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(zRot));
        this.itemRenderer.renderStatic(thrownTrident.getItemStack(), ItemDisplayContext.NONE, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, thrownTrident.level(), thrownTrident.getId());
        poseStack.popPose();
        super.render(thrownTrident, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AlternaThrownTrident entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
