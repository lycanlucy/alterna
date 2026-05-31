package io.github.lycanlucy.alterna.mixin;

import io.github.lycanlucy.alterna.common.entity.AquaticSwimmingAnimated;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSchoolingFish.class)
public abstract class AbstractSchoolingFishMixin extends AbstractFish implements AquaticSwimmingAnimated {
    @Unique
    private float alterna$clientXRotation;

    public AbstractSchoolingFishMixin(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void applyClientXRotation(CallbackInfo ci) {
        if (this.level().isClientSide()) {
            float rotation = Mth.lerp(0.1F, this.alterna$getClientXRotation(), (float) this.getDeltaMovement().y() * 400.0F);
            this.alterna$setClientXRotation(Mth.clamp(rotation, -85.0F, 85.0F));
        }
    }

    @Override
    public float alterna$getClientXRotation() {
        return alterna$clientXRotation;
    }

    @Override
    public void alterna$setClientXRotation(float rotation) {
        this.alterna$clientXRotation = rotation;
    }
}
