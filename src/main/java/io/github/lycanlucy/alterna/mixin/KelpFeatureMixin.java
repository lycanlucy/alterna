package io.github.lycanlucy.alterna.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.KelpFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KelpFeature.class)
public class KelpFeatureMixin {
    @Unique
    private boolean alterna$shouldCancelPlacement(LevelAccessor level, BlockPos blockPos) {
        return level.getBlockState(blockPos.below()).getBlock() instanceof Fallable && FallingBlock.isFree(level.getBlockState(blockPos.below(2)));
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0), cancellable = true)
    private void fixLaggyKelp1(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir, @Local WorldGenLevel worldGenLevel, @Local(ordinal = 1) BlockPos blockPos) {
        if (alterna$shouldCancelPlacement(worldGenLevel, blockPos)) {
            cir.cancel();
        }
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 1), cancellable = true)
    private void fixLaggyKelp2(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir, @Local WorldGenLevel worldGenLevel, @Local(ordinal = 1) BlockPos blockPos) {
        if (alterna$shouldCancelPlacement(worldGenLevel, blockPos)) {
            cir.cancel();
        }
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 2), cancellable = true)
    private void fixLaggyKelp3(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir, @Local WorldGenLevel worldGenLevel, @Local(ordinal = 2) BlockPos blockPos) {
        if (alterna$shouldCancelPlacement(worldGenLevel, blockPos)) {
            cir.cancel();
        }
    }
}
