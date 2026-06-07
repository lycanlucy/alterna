package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.tag.AlternaItemTags;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.entity.monster.Drowned$DrownedTridentAttackGoal")
public abstract class DrownedTridentAttackGoalMixin extends RangedAttackGoal {
    @Shadow
    @Final
    private Drowned drowned;

    public DrownedTridentAttackGoalMixin(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius) {
        super(rangedAttackMob, speedModifier, attackInterval, attackRadius);
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void acceptAllTridents(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.canUse() && this.drowned.getMainHandItem().is(AlternaItemTags.TRIDENTS));
    }
}
