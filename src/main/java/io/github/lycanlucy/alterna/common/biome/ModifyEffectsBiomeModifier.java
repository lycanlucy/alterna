package io.github.lycanlucy.alterna.common.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.alterna.config.AlternaClientConfig;
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
    public static final MapCodec<ModifyEffectsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ModifyEffectsBiomeModifier::biomes),
            Codec.INT.optionalFieldOf("grass_color").forGetter(ModifyEffectsBiomeModifier::grassColor),
            Codec.INT.optionalFieldOf("foliage_color").forGetter(ModifyEffectsBiomeModifier::foliageColor),
            Codec.INT.optionalFieldOf("water_color").forGetter(ModifyEffectsBiomeModifier::waterColor),
            Codec.INT.optionalFieldOf("water_fog_color").forGetter(ModifyEffectsBiomeModifier::waterFogColor)
    ).apply(builder, ModifyEffectsBiomeModifier::new));

    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (AlternaClientConfig.MODIFY_BIOME_COLORS.get() && phase == Phase.MODIFY && this.biomes.contains(biome)) {
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
