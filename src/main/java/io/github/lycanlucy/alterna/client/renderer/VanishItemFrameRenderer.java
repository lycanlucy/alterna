package io.github.lycanlucy.alterna.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.VanishItemFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;

public class VanishItemFrameRenderer extends EntityRenderer<VanishItemFrame> {
    public static final ModelResourceLocation MODEL_LOCATION = ModelResourceLocation.standalone(Alterna.id("block/vanish_item_frame"));
    private final ItemRenderer itemRenderer;
    private final BlockRenderDispatcher blockRenderer;

    public VanishItemFrameRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ResourceLocation getTextureLocation(VanishItemFrame entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    public void render(VanishItemFrame entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();
        Direction direction = entity.getDirection();
        Vec3 vec3 = this.getRenderOffset(entity, partialTicks);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double) direction.getStepX() * 0.46875, (double) direction.getStepY() * 0.46875, (double) direction.getStepZ() * 0.46875);
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entity.getYRot()));
        boolean flag = entity.isInvisible() || !entity.getItem().isEmpty();
        ItemStack itemstack = entity.getItem();
        if (!flag) {
            ModelManager modelmanager = this.blockRenderer.getBlockModelShaper().getModelManager();
            poseStack.pushPose();
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            this.blockRenderer
                    .getModelRenderer()
                    .renderModel(
                            poseStack.last(),
                            buffer.getBuffer(Sheets.translucentCullBlockSheet()),
                            null,
                            modelmanager.getModel(MODEL_LOCATION),
                            1.0F,
                            1.0F,
                            1.0F,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            ModelData.EMPTY,
                            null
                    );
            poseStack.popPose();
        }

        if (!itemstack.isEmpty()) {
            MapItemSavedData mapitemsaveddata = MapItem.getSavedData(itemstack, entity.level());
            if (flag) {
                poseStack.translate(0.0F, 0.0F, 0.5F);
            } else {
                poseStack.translate(0.0F, 0.0F, 0.4375F);
            }

            int j = mapitemsaveddata != null ? entity.getRotation() % 4 * 2 : entity.getRotation();
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) j * 360.0F / 8.0F));
            if (mapitemsaveddata != null) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
                float f = 0.0078125F;
                poseStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
                poseStack.translate(-64.0F, -64.0F, 0.0F);
                poseStack.translate(0.0F, 0.0F, -1.0F);
                Minecraft.getInstance().gameRenderer.getMapRenderer().render(poseStack, buffer, entity.getFramedMapId(itemstack), mapitemsaveddata, true, packedLight);
            } else {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
            }
        }

        poseStack.popPose();
    }

    public Vec3 getRenderOffset(VanishItemFrame entity, float partialTicks) {
        return new Vec3((float) entity.getDirection().getStepX() * 0.3F, -0.25, (float) entity.getDirection().getStepZ() * 0.3F);
    }

    @Override
    protected boolean shouldShowName(VanishItemFrame entity) {
        if (Minecraft.renderNames()
                && !entity.getItem().isEmpty()
                && entity.getItem().has(DataComponents.CUSTOM_NAME)
                && this.entityRenderDispatcher.crosshairPickEntity == entity) {
            double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
            float f = entity.isDiscrete() ? 32.0F : 64.0F;
            return d0 < (double) (f * f);
        } else {
            return false;
        }
    }

    @Override
    protected void renderNameTag(VanishItemFrame entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        super.renderNameTag(entity, entity.getItem().getHoverName(), poseStack, bufferSource, packedLight, partialTick);
    }
}
