package io.github.lycanlucy.alterna.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue MODIFY_BIOME_COLORS = BUILDER
            .comment("Whether to use this mod's biome colors (Currently only affects water)")
            .define("modifyBiomeColors", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
