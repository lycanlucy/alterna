package io.github.lycanlucy.alterna.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected int autoSpinAttackTicks;

    @Shadow
    protected abstract float getWaterSlowDown();

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getSpeed()F"))
    private void fixRiptideMomentumLoss(Vec3 travelVector, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef waterFriction) {
        if (this.autoSpinAttackTicks > 0) {
            waterFriction.set(this.isSprinting() ? 0.9F : this.getWaterSlowDown());
        }
    }
}
