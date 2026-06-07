package io.github.lycanlucy.alterna.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.model.GliderModel;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class GliderLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public static final ResourceLocation TEXTURE = Alterna.id("textures/entity/glider/glider.png");
    public static final ResourceLocation DYED_TEXTURE = Alterna.id("textures/entity/glider/glider_dyed.png");
    public static final ResourceLocation DYED_OVERLAY_TEXTURE = Alterna.id("textures/entity/glider/glider_dyed_overlay.png");
    private final Model model;

    public GliderLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new GliderModel(modelSet.bakeLayer(GliderModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (GliderItem.isGliding(livingEntity)) {
            poseStack.pushPose();

            ItemStack itemStack = livingEntity.getMainHandItem().is(AlternaItems.GLIDER) ? livingEntity.getMainHandItem() : livingEntity.getOffhandItem();
            poseStack.translate(0.0, -1.25, 0.0);

            DyedItemColor dyedItemColor = itemStack.get(DataComponents.DYED_COLOR);
            if (dyedItemColor == null) {
                VertexConsumer base = bufferSource.getBuffer(this.model.renderType(TEXTURE));
                this.model.renderToBuffer(poseStack, base, packedLight, OverlayTexture.NO_OVERLAY);
            } else {
                VertexConsumer base = bufferSource.getBuffer(this.model.renderType(DYED_TEXTURE));
                this.model.renderToBuffer(poseStack, base, packedLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.opaque(dyedItemColor.rgb()));
                VertexConsumer overlay = bufferSource.getBuffer(this.model.renderType(DYED_OVERLAY_TEXTURE));
                this.model.renderToBuffer(poseStack, overlay, packedLight, OverlayTexture.NO_OVERLAY);
            }

            poseStack.popPose();
        }
    }
}