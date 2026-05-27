package io.github.lycanlucy.alterna.common.item;

import io.github.lycanlucy.alterna.common.entity.AlternaThrownTrident;
import io.github.lycanlucy.alterna.registry.AlternaDataComponents;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlternaTridentItem extends Item implements ProjectileItem {
    public static final int THROW_THRESHOLD_TIME = 10;
    public static final float TRIDENT_BASE_DAMAGE = 8.0f;
    public static final float SUNKEN_TRIDENT_BASE_DAMAGE = 4.0f;
    public static final float SHOOT_POWER = 2.5f;

    public AlternaTridentItem(Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes(float attackDamage) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.9F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(), 1.0f, 2);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, @NotNull ItemStack repairCandidate) {
        return stack.is(AlternaItems.TRIDENT) && repairCandidate.is(Items.PRISMARINE_SHARD);
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int useTime = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (useTime >= THROW_THRESHOLD_TIME) {
                float spinAttackStrength = EnchantmentHelper.getTridentSpinAttackStrength(stack, player);
                if (!(spinAttackStrength > 0.0F) || player.isInWaterOrRain()) {
                    if (!isTooDamagedToUse(stack)) {
                        Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW);
                        if (!level.isClientSide) {
                            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                            if (spinAttackStrength == 0.0F) {
                                AlternaThrownTrident thrownTrident = new AlternaThrownTrident(level, player, stack);
                                thrownTrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, SHOOT_POWER, 1.0f);
                                if (player.hasInfiniteMaterials()) {
                                    thrownTrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                }

                                level.addFreshEntity(thrownTrident);
                                level.playSound(null, thrownTrident, holder.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
                                if (!player.hasInfiniteMaterials()) {
                                    player.getInventory().removeItem(stack);
                                }
                            }
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        if (spinAttackStrength > 0.0F) {
                            float yRot = player.getYRot();
                            float xRot = player.getXRot();
                            float x = -Mth.sin(yRot * (float) (Math.PI / 180.0)) * Mth.cos(xRot * (float) (Math.PI / 180.0));
                            float y = -Mth.sin(xRot * (float) (Math.PI / 180.0));
                            float z = Mth.cos(yRot * (float) (Math.PI / 180.0)) * Mth.cos(xRot * (float) (Math.PI / 180.0));
                            float f = Mth.sqrt(x * x + y * y + z * z);
                            x *= spinAttackStrength / f;
                            y *= spinAttackStrength / f;
                            z *= spinAttackStrength / f;
                            player.push(x, y, z);
                            player.startAutoSpinAttack(20, stack.getOrDefault(AlternaDataComponents.PROJECTILE_BASE_DAMAGE, TRIDENT_BASE_DAMAGE), stack);
                            if (player.onGround()) {
                                player.move(MoverType.SELF, new Vec3(0.0, 1.1999999f, 0.0));
                            }

                            level.playSound(null, player, holder.value(), SoundSource.PLAYERS, 1.0f, 1.0f);
                        }
                    }
                }
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isTooDamagedToUse(itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getTridentSpinAttackStrength(itemstack, player) > 0.0f && !player.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getEnchantmentValue(@NotNull ItemStack stack) {
        return stack.is(AlternaItems.SUNKEN_TRIDENT) ? 0 : 1;
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position pos, ItemStack stack, @NotNull Direction direction) {
        AlternaThrownTrident thrownTrident = new AlternaThrownTrident(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        thrownTrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownTrident;
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_TRIDENT_ACTIONS.contains(itemAbility);
    }
}
