package io.github.lycanlucy.alterna.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.lycanlucy.alterna.data.server.tag.AlternaItemTags;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
    public ItemInHandLayerMixin(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void renderItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (!itemStack.is(AlternaItemTags.TRIDENTS) || !(this.getParentModel() instanceof HumanoidModel<?>))
            return;
        boolean isRightArmUsingTrident = arm == HumanoidArm.RIGHT && ((HumanoidModel<?>) this.getParentModel()).rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR;
        boolean isLeftArmUsingTrident = arm == HumanoidArm.LEFT && ((HumanoidModel<?>) this.getParentModel()).leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR;

        if (isRightArmUsingTrident || isLeftArmUsingTrident) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        }
    }
}
