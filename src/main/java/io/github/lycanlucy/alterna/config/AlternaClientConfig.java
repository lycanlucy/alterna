package io.github.lycanlucy.alterna.config;

import io.github.lycanlucy.alterna.util.ConfigHelper;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue MODIFY_BIOME_COLORS = BUILDER
            .comment(ConfigHelper.MODIFY_BIOME_COLORS_TOOLTIP)
            .define(ConfigHelper.MODIFY_BIOME_COLORS_ID, true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
