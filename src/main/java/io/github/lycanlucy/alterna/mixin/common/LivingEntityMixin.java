package io.github.lycanlucy.alterna.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    protected int autoSpinAttackTicks;

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    private void changeRiptideWaterInertia(Vec3 travelVector, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef waterInertia) {
        if (this.autoSpinAttackTicks > 0) {
            waterInertia.set((float) AlternaServerConfig.riptideWaterInertia());
        }
    }
}
