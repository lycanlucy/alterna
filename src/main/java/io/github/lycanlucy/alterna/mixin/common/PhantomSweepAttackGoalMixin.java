package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.entity.monster.Phantom$PhantomAttackStrategyGoal")
public abstract class PhantomSweepAttackGoalMixin {
    @Shadow
    @Final
    Phantom this$0;

    @Shadow
    protected abstract void setAnchorAboveTarget();

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void avoidSwoopingAtHealthyPlayers(CallbackInfo ci) {
        if (AlternaServerConfig.opportunisticPhantoms() && this$0.getTarget() instanceof Player player) {
            if (player.getHealth() > player.getMaxHealth() / 2) {
                this.setAnchorAboveTarget();
                ci.cancel();
            }
        }
    }
}