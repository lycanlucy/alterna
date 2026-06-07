package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import net.minecraft.world.entity.ai.goal.FollowFlockLeaderGoal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(FollowFlockLeaderGoal.class)
public class FollowFlockLeaderGoalMixin {
    // Based on https://github.com/SteveKunG/FishNoStuck/blob/26.1/common/src/main/java/com/stevekung/fishnostuck/mixin/MixinFollowFlockLeaderGoal.java
    @ModifyArg(method = "canUse", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;", ordinal = 1))
    private Predicate<AbstractSchoolingFish> fixGettingStuck(Predicate<AbstractSchoolingFish> predicate) {
        if (AlternaServerConfig.schoolingFix()) {
            return predicate.and(Predicate.not(AbstractSchoolingFish::hasFollowers));
        }
        return predicate;
    }
}
