package io.github.lycanlucy.alterna.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.common.block.ItemRackBlock;
import io.github.lycanlucy.alterna.common.block.ItemRackBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemRackRenderer implements BlockEntityRenderer<ItemRackBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ItemRackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ItemRackBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() != null) {
            Direction direction = blockEntity.getBlockState().getValue(ItemRackBlock.FACING);
            ItemStack itemstack = blockEntity.getItem();
            if (!itemstack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 0.5F, 0.5F);
                poseStack.translate((double) direction.getStepX() * -0.335, (double) direction.getStepY() * -0.335, (double) direction.getStepZ() * -0.335);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - direction.toYRot()));
                poseStack.mulPose(Axis.ZP.rotationDegrees(blockEntity.getRotation() * 360.0F / 8.0F));
                int lightColor = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockState(), blockEntity.getBlockPos().relative(direction));
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.valueOf("ALTERNA_FIXED_FULLSIZE"), lightColor, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
