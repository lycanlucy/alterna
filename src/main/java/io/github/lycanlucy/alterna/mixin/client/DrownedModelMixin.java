package io.github.lycanlucy.alterna.mixin.client;

import io.github.lycanlucy.alterna.data.list.AlternaItemTags;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedModel.class)
public abstract class DrownedModelMixin<T extends Zombie> extends ZombieModel<T> {
    public DrownedModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/monster/Zombie;FFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ZombieModel;prepareMobModel(Lnet/minecraft/world/entity/LivingEntity;FFF)V"))
    private void poseArms(T entity, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        ItemStack itemStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.is(AlternaItemTags.TRIDENTS) && entity.isAggressive()) {
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = ArmPose.THROW_SPEAR;
            }
        }
    }
}
