package io.github.lycanlucy.alterna.common.entity.goal;

import io.github.lycanlucy.alterna.data.server.tag.AlternaItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class AlternaTridentAttackGoal extends RangedAttackGoal {
    protected final Mob mob;

    public AlternaTridentAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius) {
        super(rangedAttackMob, speedModifier, attackInterval, attackRadius);
        this.mob = (Mob) rangedAttackMob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.mob.getMainHandItem().is(AlternaItemTags.TRIDENTS);
    }

    @Override
    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.mob.startUsingItem(InteractionHand.MAIN_HAND);
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.stopUsingItem();
        this.mob.setAggressive(false);
    }
}
