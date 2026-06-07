package io.github.lycanlucy.alterna.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private void setTridentData(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfo ci, @Local ThrownTrident thrownTrident) {
        thrownTrident.setData(AlternaAttachments.RETURN_SLOT, (byte) ((Player) entityLiving).getInventory().selected);
    }
}
