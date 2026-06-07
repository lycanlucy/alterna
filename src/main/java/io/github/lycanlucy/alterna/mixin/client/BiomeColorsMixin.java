package io.github.lycanlucy.alterna.mixin.client;

import io.github.lycanlucy.alterna.client.AlternaClientColors;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Shadow
    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Inject(method = "getAverageWaterColor", at = @At("HEAD"), cancellable = true)
    private static void changeWaterColor(BlockAndTintGetter level, BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
        if (AlternaClientConfig.modifyBiomeColors()) {
            cir.setReturnValue(getAverageColor(level, blockPos, AlternaClientColors.WATER_COLOR_RESOLVER));
        }
    }
}