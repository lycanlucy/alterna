package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.item.TridentProperties;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import io.github.lycanlucy.alterna.registry.AlternaDataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow {
    protected ThrownTridentMixin(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void init(Level level, LivingEntity shooter, ItemStack pickupItemStack, CallbackInfo ci) {
        this.setData(AlternaAttachments.ORIGINAL_STACK, pickupItemStack.copy());
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void init(Level level, double x, double y, double z, ItemStack pickupItemStack, CallbackInfo ci) {
        this.setData(AlternaAttachments.ORIGINAL_STACK, pickupItemStack.copy());
    }

    @ModifyVariable(method = "onHitEntity", at = @At("STORE"), ordinal = 0)
    private float changeDamage(float original) {
        return getData(AlternaAttachments.ORIGINAL_STACK).getOrDefault(AlternaDataComponents.TRIDENT_PROPERTIES, TridentProperties.DEFAULT).projectileDamage();
    }

    @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
    private int modifyLoyalty(int original) {
        if (this.getOwner() instanceof Player) {
            return original + getData(AlternaAttachments.ORIGINAL_STACK).getOrDefault(AlternaDataComponents.TRIDENT_PROPERTIES, TridentProperties.DEFAULT).baseLoyalty();
        }
        return original;
    }

    @Inject(method = "tryPickup", at = @At("HEAD"), cancellable = true)
    private void returnToProperSlot(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (this.isNoPhysics() && this.ownedBy(player)) {
            cir.setReturnValue(player.getInventory().add(getData(AlternaAttachments.RETURN_SLOT), this.getPickupItem()));
        }
    }
}
