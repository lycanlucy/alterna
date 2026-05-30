package io.github.lycanlucy.alterna.mixin;

import io.github.lycanlucy.alterna.common.entity.AlternaThrownTrident;
import io.github.lycanlucy.alterna.common.entity.goal.AlternaTridentAttackGoal;
import io.github.lycanlucy.alterna.data.list.AlternaItemTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie {
    public DrownedMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "addBehaviourGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Drowned$DrownedTridentAttackGoal;<init>(Lnet/minecraft/world/entity/monster/RangedAttackMob;DIF)V"))
    private void addCustomGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(2, new AlternaTridentAttackGoal((RangedAttackMob) this, 1.0, 70, 7.0F));
    }

    @Inject(method = "performRangedAttack", at = @At("HEAD"), cancellable = true)
    private void changeThrownTrident(LivingEntity target, float distanceFactor, CallbackInfo ci) {
        AlternaThrownTrident thrownTrident = new AlternaThrownTrident(this.level(), this, this.getMainHandItem().is(AlternaItemTags.TRIDENTS) ? this.getMainHandItem() : new ItemStack(AlternaItems.SUNKEN_TRIDENT.get()));
        double x = target.getX() - this.getX();
        double y = target.getY(0.3333333333333333) - thrownTrident.getY();
        double z = target.getZ() - this.getZ();
        double d = Math.sqrt(x * x + z * z);
        thrownTrident.shoot(x, y + d * 0.2F, z, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(thrownTrident);
        ci.cancel();
    }

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
    private void alterDefaultEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (random.nextInt(100) < 15) {
            if (random.nextInt(5) == 0) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
            } else {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AlternaItems.SUNKEN_TRIDENT.get()));
            }
        }
        ci.cancel();
    }

    @Inject(method = "canReplaceCurrentItem", at = @At("HEAD"), cancellable = true)
    private void alterItemReplacementLogic(ItemStack candidate, ItemStack existing, CallbackInfoReturnable<Boolean> cir) {
        if (existing.is(Items.NAUTILUS_SHELL)) {
            cir.setReturnValue(false);
        } else if (existing.is(AlternaItems.SUNKEN_TRIDENT)) {
            cir.setReturnValue(candidate.is(AlternaItems.TRIDENT) || candidate.getDamageValue() < existing.getDamageValue());
        } else if (existing.is(AlternaItemTags.TRIDENTS)) {
            cir.setReturnValue(candidate.is(AlternaItemTags.TRIDENTS) && candidate.getDamageValue() < existing.getDamageValue());
        } else {
            cir.setReturnValue(candidate.is(AlternaItemTags.TRIDENTS) || super.canReplaceCurrentItem(candidate, existing));
        }
    }
}
