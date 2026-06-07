package io.github.lycanlucy.alterna.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.client.renderer.ReplacedModelRenderer;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow
    protected M model;

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void changeModel(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (this instanceof ReplacedModelRenderer renderer && renderer.alterna$enabled() && entity.hasData(AlternaAttachments.MOB_VARIANT)) {
            renderer.alterna$getModelReplacements().stream().filter(pair -> MobVariant.getFor(entity).model().equals(pair.getSecond())).findFirst().ifPresent(pair -> this.model = (M) pair.getFirst());
        }
    }

    @Inject(method = "setupRotations", at = @At("TAIL"))
    private void changeRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
        if (AlternaClientConfig.immersiveFish() && entity instanceof AbstractFish && entity.isInWater()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(entity.getData(AlternaAttachments.SWIM_ROT)));
        }
    }

    @ModifyArg(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;itemEntityTranslucentCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private ResourceLocation changeTexture1(ResourceLocation location, @Local(argsOnly = true) T entity) {
        if (this instanceof ReplacedModelRenderer renderer && renderer.alterna$enabled() && entity.hasData(AlternaAttachments.MOB_VARIANT)) {
            return MobVariant.getFor(entity).texture();
        }
        return location;
    }

    @ModifyArg(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private ResourceLocation changeTexture2(ResourceLocation location, @Local(argsOnly = true) T entity) {
        if (this instanceof ReplacedModelRenderer renderer && renderer.alterna$enabled() && entity.hasData(AlternaAttachments.MOB_VARIANT)) {
            return MobVariant.getFor(entity).texture();
        }
        return location;
    }

    @ModifyArg(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;outline(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"))
    private ResourceLocation changeTexture3(ResourceLocation location, @Local(argsOnly = true) T entity) {
        if (this instanceof ReplacedModelRenderer renderer && renderer.alterna$enabled() && entity.hasData(AlternaAttachments.MOB_VARIANT)) {
            return MobVariant.getFor(entity).texture();
        }
        return location;
    }
}
