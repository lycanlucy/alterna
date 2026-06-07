package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Drowned.class)
public abstract class DrownedMixin extends Zombie {
    public DrownedMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(method = "addBehaviourGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Drowned$DrownedTridentAttackGoal;<init>(Lnet/minecraft/world/entity/monster/RangedAttackMob;DIF)V"), index = 2)
    private int changeAttackInterval(int original) {
        return 70;
    }

    @ModifyArg(method = "addBehaviourGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Drowned$DrownedTridentAttackGoal;<init>(Lnet/minecraft/world/entity/monster/RangedAttackMob;DIF)V"), index = 3)
    private float changeAttackRadius(float original) {
        return 7.0F;
    }

    @Redirect(method = "populateDefaultEquipmentSlots", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Drowned;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V", ordinal = 0))
    private void changeTrident(Drowned instance, EquipmentSlot equipmentSlot, ItemStack itemStack) {
        instance.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(AlternaItems.SUNKEN_TRIDENT.get()));
        instance.setDropChance(EquipmentSlot.MAINHAND, 0.1f);
    }

    @ModifyVariable(method = "performRangedAttack", at = @At("STORE"), ordinal = 0)
    private ThrownTrident respectHeldTrident(ThrownTrident original) {
        return new ThrownTrident(this.level(), this, this.getMainHandItem().is(AlternaItemTags.TRIDENTS) ? this.getMainHandItem() : new ItemStack(AlternaItems.SUNKEN_TRIDENT.get()));
    }
}
