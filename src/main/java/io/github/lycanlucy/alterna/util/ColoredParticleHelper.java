package io.github.lycanlucy.alterna.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import org.joml.Vector3f;

public interface ColoredParticleHelper {
    static Vector3f getWaterColor(ClientLevel level, double x, double y, double z) {
        int waterColor = BiomeColors.getAverageWaterColor(level, BlockPos.containing(x, y, z));
        return new Vector3f(FastColor.ARGB32.red(waterColor) / 255.0f, FastColor.ARGB32.green(waterColor) / 255.0f, FastColor.ARGB32.blue(waterColor) / 255.0f);
    }
}
