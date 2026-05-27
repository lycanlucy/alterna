package io.github.lycanlucy.alterna.mixin;

import io.github.lycanlucy.alterna.common.entity.AlternaThrownTrident;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
    @Inject(method = "onProjectileHit", at = @At("HEAD"))
    private void allowCustomTridentInteraction(Level level, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo ci) {
        BlockPos blockPos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockPos) && projectile instanceof AlternaThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(blockPos, true);
        }
    }
}
