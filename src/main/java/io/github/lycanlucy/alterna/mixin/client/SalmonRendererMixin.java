package io.github.lycanlucy.alterna.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.client.model.OceanSalmonModel;
import io.github.lycanlucy.alterna.client.model.RiverSalmonModel;
import io.github.lycanlucy.alterna.client.renderer.ReplacedModelRenderer;
import io.github.lycanlucy.alterna.common.entity.AquaticSwimmingAnimated;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SalmonRenderer.class)
public abstract class SalmonRendererMixin extends MobRenderer<Salmon, SalmonModel<Salmon>> implements ReplacedModelRenderer {
    @Unique
    private final ArrayList<Pair<EntityModel<?>, String>> alterna$REPLACED_MODELS = new ArrayList<>();

    public SalmonRendererMixin(EntityRendererProvider.Context context, SalmonModel<Salmon> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeModel(EntityRendererProvider.Context context, CallbackInfo ci) {
        alterna$REPLACED_MODELS.add(new Pair<>(new OceanSalmonModel(context.bakeLayer(OceanSalmonModel.LAYER_LOCATION)), MobVariant.OCEAN_SALMON_MODEL));
        alterna$REPLACED_MODELS.add(new Pair<>(new RiverSalmonModel(context.bakeLayer(RiverSalmonModel.LAYER_LOCATION)), MobVariant.RIVER_SALMON_MODEL));
    }

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/animal/Salmon;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V", at = @At("HEAD"), cancellable = true)
    private void changeRotations(Salmon entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        float rotationMultiplier = 1.0F;
        float sineMultiplier = 1.0F;
        if (!entity.isInWater()) {
            rotationMultiplier = 1.3F;
            sineMultiplier = 1.7F;
        }

        float rotation = rotationMultiplier * 4.3F * Mth.sin(sineMultiplier * 0.6F * bob);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        if (entity.isInWater()) {
            AquaticSwimmingAnimated.applyClientXRotation(poseStack, (AquaticSwimmingAnimated) entity);
        } else {
            poseStack.translate(0.2F, 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }
        ci.cancel();
    }

    @Override
    public List<Pair<EntityModel<?>, String>> alterna$getReplacedModels() {
        return alterna$REPLACED_MODELS;
    }
}
