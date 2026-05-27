package io.github.lycanlucy.alterna.util;

import com.mojang.serialization.MapCodec;
import io.github.lycanlucy.alterna.registry.AlternaBiomeModifierSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record ModifyEffectsBiomeModifier(HolderSet<Biome> biomes, Optional<Integer> grassColor,
                                         Optional<Integer> foliageColor,
                                         Optional<Integer> waterColor,
                                         Optional<Integer> waterFogColor) implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase == Phase.MODIFY && this.biomes.contains(biome)) {
            grassColor.ifPresent(value -> builder.getSpecialEffects().grassColorOverride(value));
            foliageColor.ifPresent(value -> builder.getSpecialEffects().foliageColorOverride(value));
            waterColor.ifPresent(value -> builder.getSpecialEffects().waterColor(value));
            waterFogColor.ifPresent(value -> builder.getSpecialEffects().waterFogColor(value));
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return AlternaBiomeModifierSerializers.MODIFY_EFFECTS.get();
    }
}
