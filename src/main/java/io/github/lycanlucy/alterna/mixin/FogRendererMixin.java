package io.github.lycanlucy.alterna.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.client.AlternaClientColors;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @ModifyVariable(method = "setupColor", at = @At("STORE"), ordinal = 1)
    private static int changeWaterFogColor(int original, @Local(argsOnly = true) Camera activeRenderInfo, @Local(argsOnly = true) ClientLevel level) {
        if (AlternaClientConfig.modifyBiomeColors()) {
            Biome biome = level.getBiome(BlockPos.containing(activeRenderInfo.getPosition())).value();
            if (AlternaClientColors.BIOME_WATER_FOG_COLORS.containsKey(biome)) {
                return AlternaClientColors.BIOME_WATER_FOG_COLORS.getInt(biome);
            }
            if (AlternaClientColors.BIOME_WATER_COLORS.containsKey(biome)) {
                return AlternaClientColors.BIOME_WATER_COLORS.getInt(biome);
            }
        }
        return original;
    }
}