package io.github.lycanlucy.alterna.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
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
    private static boolean alterna$isOkToPlace(WorldGenLevel level, BlockPos blockPos) {
        return !(level.getBlockState(blockPos.below()).getBlock() instanceof Fallable) || !FallingBlock.isFree(level.getBlockState(blockPos.below()));
    }

    @Inject(method = "place", at = @At("HEAD"), cancellable = true)
    private void preventPlacingOnFallingBlocks(FeaturePlaceContext<NoneFeatureConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
        int i = 0;
        WorldGenLevel level = context.level();
        BlockPos blockPos = context.origin();
        RandomSource randomSource = context.random();
        int height = level.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
        BlockPos blockpos1 = new BlockPos(blockPos.getX(), height, blockPos.getZ());
        if (level.getBlockState(blockpos1).is(Blocks.WATER)) {
            BlockState blockState = Blocks.KELP.defaultBlockState();
            BlockState blockState1 = Blocks.KELP_PLANT.defaultBlockState();
            int k = 1 + randomSource.nextInt(10);

            for (int l = 0; l <= k; l++) {
                if (level.getBlockState(blockpos1).is(Blocks.WATER) && level.getBlockState(blockpos1.above()).is(Blocks.WATER) && blockState1.canSurvive(level, blockpos1)) {
                    if (l == k) {
                        if (alterna$isOkToPlace(level, blockpos1)) {
                            level.setBlock(blockpos1, blockState.setValue(KelpBlock.AGE, randomSource.nextInt(4) + 20), 2);
                            i++;
                        }
                    } else {
                        if (alterna$isOkToPlace(level, blockpos1)) {
                            level.setBlock(blockpos1, blockState1, 2);
                        }
                    }
                } else if (l > 0) {
                    BlockPos below = blockpos1.below();
                    if (blockState.canSurvive(level, below) && !level.getBlockState(below.below()).is(Blocks.KELP)) {
                        if (alterna$isOkToPlace(level, below)) {
                            level.setBlock(below, blockState.setValue(KelpBlock.AGE, randomSource.nextInt(4) + 20), 2);
                            i++;
                        }
                    }
                    break;
                }

                blockpos1 = blockpos1.above();
            }
        }

        cir.setReturnValue(i > 0);
    }
}
