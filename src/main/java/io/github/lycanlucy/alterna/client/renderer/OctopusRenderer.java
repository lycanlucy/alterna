package io.github.lycanlucy.alterna.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.model.OctopusModel;
import io.github.lycanlucy.alterna.common.entity.Octopus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class OctopusRenderer extends MobRenderer<Octopus, OctopusModel> {
    private static final ResourceLocation TEXTURE = Alterna.id("textures/entity/octopus.png");

    public OctopusRenderer(EntityRendererProvider.Context context) {
        super(context, new OctopusModel(context.bakeLayer(OctopusModel.LAYER_LOCATION)), 0.6F);
    }

    @Override
    protected void setupRotations(Octopus entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        if (entity.isInWater()) {
            float f = Mth.lerp(partialTick, entity.xBodyRotO, entity.xBodyRot);
            float f1 = Mth.lerp(partialTick, entity.zBodyRotO, entity.zBodyRot);
            poseStack.translate(0.0F, 0.5F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yBodyRot));
            poseStack.mulPose(Axis.XP.rotationDegrees(f));
            poseStack.mulPose(Axis.YP.rotationDegrees(f1));
            poseStack.translate(0.0F, -0.5F, 0.0F);
        } else {
            super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);

        }
    }

    @Override
    public ResourceLocation getTextureLocation(Octopus entity) {
        return TEXTURE;
    }
}
