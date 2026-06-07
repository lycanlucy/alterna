package io.github.lycanlucy.alterna.common.item;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import io.github.lycanlucy.alterna.registry.AlternaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class GliderItem extends Item {
    public static final AttributeModifier GLIDER_GRAVITY_MODIFIER = new AttributeModifier(Alterna.id("gliding"), -0.076, AttributeModifier.Operation.ADD_VALUE);

    public GliderItem(Properties properties) {
        super(properties);
    }

    public static boolean isUsable(ItemStack itemStack) {
        return itemStack.getDamageValue() < itemStack.getMaxDamage() - 1;
    }

    public static void glide(LivingEntity entity) {
        if (shouldGlide(entity)) {
            int glidingTicks = getGlidingTicks(entity);
            if (glidingTicks == 0) {
                setGliderMomentum(entity, 1.0F + (float) (entity.getDeltaMovement().horizontalDistance() * 2.0F));
            }
            setGlidingTicks(entity, glidingTicks + 1);

            int nextGlideTick = glidingTicks + 1;
            if (nextGlideTick % 20 == 0) {
                boolean isInMainHand = entity.getMainHandItem().is(AlternaItems.GLIDER);
                ItemStack itemStack = isInMainHand ? entity.getMainHandItem() : entity.getOffhandItem();
                itemStack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(isInMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND));
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
            double x = Mth.lerp(0.1, entity.getDeltaMovement().x, entity.getForward().normalize().x * 0.4 * getGliderMomentum(entity));
            double z = Mth.lerp(0.1, entity.getDeltaMovement().z, entity.getForward().normalize().z * 0.4 * getGliderMomentum(entity));
            entity.setDeltaMovement(x, entity.getDeltaMovement().y, z);
            if (shouldBoost(entity.level(), entity.blockPosition())) {
                entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.4, entity.getDeltaMovement().z());
                if (!GliderItem.isBoosting(entity)) {
                    GliderItem.setBoosting(entity, true);
                    entity.playSound(AlternaSounds.GLIDER_BOOST.get());
                    if (entity instanceof ServerPlayer) {
                        AlternaTriggers.BOOSTED_WITH_GLIDER.get().trigger((ServerPlayer) entity);
                    }
                }
            } else if (!shouldBoost(entity.level(), entity.blockPosition()) && GliderItem.isBoosting(entity)) {
                GliderItem.setBoosting(entity, false);
            }
        }
        boolean shouldStopGliding = !isHoldingUsableGlider(entity) || entity.onGround() || entity.isInFluidType() || entity.isSpectator() || entity.isFallFlying() || entity instanceof Player player && player.getAbilities().flying;
        if (shouldStopGliding && getGlidingTicks(entity) != 0) {
            setGlidingTicks(entity, 0);
        }
        handleGravity(entity);
    }

    public static void handleGravity(LivingEntity entity) {
        AttributeInstance gravity = entity.getAttribute(Attributes.GRAVITY);
        if (gravity == null) return;
        if (gravity.hasModifier(GLIDER_GRAVITY_MODIFIER.id())) {
            if (!isGliding(entity)) {
                gravity.removeModifier(GLIDER_GRAVITY_MODIFIER.id());
                entity.resetFallDistance();
                entity.playSound(AlternaSounds.GLIDER_CLOSE.get(), 1.0F, 1.0F);
            }
        } else if (isGliding(entity)) {
            gravity.addTransientModifier(GLIDER_GRAVITY_MODIFIER);
            Vec3 movement = entity.getDeltaMovement().add(0.0, 0.45, 0.0);
            movement = new Vec3(movement.x, Math.clamp(movement.y, -0.2, 0.8), movement.z);
            entity.setDeltaMovement(movement);
            entity.playSound(AlternaSounds.GLIDER_OPEN.get(), 1.0F, 1.0F);
        }
    }

    public static boolean isHoldingUsableGlider(LivingEntity entity) {
        boolean mainHand = entity.getMainHandItem().is(AlternaItems.GLIDER) && isUsable(entity.getMainHandItem());
        boolean offHand = entity.getOffhandItem().is(AlternaItems.GLIDER) && isUsable(entity.getOffhandItem());
        return (mainHand || offHand);
    }

    public static boolean shouldGlide(LivingEntity entity) {
        return isHoldingUsableGlider(entity) && entity.fallDistance > 1.24 && !entity.isFallFlying();
    }

    public static boolean shouldBoost(Level level, BlockPos pos) {
        for (int i = 1; i <= 18; ++i) {
            BlockPos blockpos = pos.below(i);
            BlockState blockstate = level.getBlockState(blockpos);
            boolean signalFire = blockstate.getOptionalValue(CampfireBlock.SIGNAL_FIRE).orElse(false);
            boolean noSignalFire = i <= 6;
            if (blockstate.is(BlockTags.CAMPFIRES) && blockstate.getOptionalValue(CampfireBlock.LIT).orElse(false)) {
                return signalFire || noSignalFire;
            }
        }
        return CampfireBlock.isLitCampfire(level.getBlockState(pos));
    }

    public static boolean isGliding(LivingEntity entity) {
        return isHoldingUsableGlider(entity) && getGlidingTicks(entity) > 0;
    }

    public static boolean isBoosting(LivingEntity entity) {
        return entity.getData(AlternaAttachments.GLIDER_BOOSTING);
    }

    public static void setBoosting(LivingEntity entity, boolean boosting) {
        entity.setData(AlternaAttachments.GLIDER_BOOSTING, boosting);
    }

    public static int getGlidingTicks(LivingEntity entity) {
        return entity.getData(AlternaAttachments.GLIDER_GLIDING_TICKS);
    }

    public static void setGlidingTicks(LivingEntity entity, int ticks) {
        entity.setData(AlternaAttachments.GLIDER_GLIDING_TICKS, ticks);
    }

    public static float getGliderMomentum(LivingEntity entity) {
        return entity.getData(AlternaAttachments.GLIDER_MOMENTUM);
    }

    public static void setGliderMomentum(LivingEntity entity, float momentum) {
        entity.setData(AlternaAttachments.GLIDER_MOMENTUM, momentum);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.PHANTOM_MEMBRANE);
    }
}
