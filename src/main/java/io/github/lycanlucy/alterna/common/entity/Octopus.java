package io.github.lycanlucy.alterna.common.entity;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Octopus extends WaterAnimal {
    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
    public float xBodyRot;
    public float xBodyRotO;
    public float zBodyRot;
    public float zBodyRotO;

    public Octopus(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.12F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.STEP_HEIGHT, 1.0).add(Attributes.MOVEMENT_SPEED, 1.0).add(Attributes.MAX_HEALTH, 12.0).add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1.0, 10));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.xBodyRotO = this.xBodyRot;
        this.zBodyRotO = this.zBodyRot;

        if (this.isInWaterOrBubble()) {
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            this.zBodyRot = this.zBodyRot + (float) Math.PI * 1.5F;
            this.xBodyRot = this.xBodyRot + (-((float) Mth.atan2(d0, vec3.y)) * (180.0F / (float) Math.PI) - this.xBodyRot) * 0.1F;
        }
    }


    @Override
    public void travel(Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.GLASS_BOTTLE) && !this.isBaby()) {
            player.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
            ItemStack filledResult = ItemUtils.createFilledResult(itemStack, player, AlternaItems.INK_BOTTLE.get().getDefaultInstance());
            player.setItemInHand(hand, filledResult);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * this.getWalkAnimationSpeed(), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 5.0F : 10.0F;
    }

    @Override
    public void tick() {
        if (this.level().isClientSide()) {
            this.idleAnimationState.animateWhen(true, this.tickCount);
            if (this.isInWater()) {
                for (int i = 0; i < Mth.clamp(Math.round(this.walkAnimation.speed() * 2), 0, 4); i++) {
                    Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getRandomX(0.5), this.getY(), this.getRandomZ(0.5));
                    this.level().addParticle(ParticleTypes.BUBBLE, vec3.x, vec3.y + 0.5, vec3.z, 0.0, 0.0, 0.0);
                }
            }
        }

        super.tick();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount) && this.getLastHurtByMob() != null) {
            if (!this.level().isClientSide && this.isInWaterOrBubble()) {
                this.spawnInk();
            }

            return true;
        } else {
            return false;
        }
    }

    private void spawnInk() {
        this.makeSound(SoundEvents.SQUID_SQUIRT);
        Vec3 vec3 = this.rotateVector(new Vec3(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());

        for (int i = 0; i < 30; i++) {
            Vec3 vec31 = this.rotateVector(new Vec3((double) this.random.nextFloat() * 0.6 - 0.3, -1.0, (double) this.random.nextFloat() * 0.6 - 0.3));
            Vec3 vec32 = vec31.scale(0.3 + (double) (this.random.nextFloat() * 2.0F));
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.SQUID_INK, vec3.x, vec3.y + 0.5, vec3.z, 0, vec32.x, vec32.y, vec32.z, 0.1F);
        }
    }

    private Vec3 rotateVector(Vec3 vector) {
        Vec3 vec3 = vector.xRot(this.xBodyRotO * (float) (Math.PI / 180.0));
        return vec3.yRot(-this.yBodyRotO * (float) (Math.PI / 180.0));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AlternaSounds.OCTOPUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AlternaSounds.OCTOPUS_DEATH.get();
    }

    @Override
    protected SoundEvent getSwimSound() {
        return AlternaSounds.OCTOPUS_SWIM.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(AlternaSounds.OCTOPUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }
}
