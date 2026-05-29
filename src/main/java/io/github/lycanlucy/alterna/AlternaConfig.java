package io.github.lycanlucy.alterna;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue MODIFY_BIOME_COLORS = BUILDER
            .comment("Whether to use this mod's biome colors (Currently only affects water)")
            .define("modifyBiomeColors", true);

    static final ModConfigSpec SPEC = BUILDER.build();
}
