package io.github.lycanlucy.alterna.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaClientConfig extends AlternaAbstractConfig {
    public static final BooleanConfig MODIFY_BIOME_COLORS = define(new BooleanConfig(
            "modifyBiomeColors",
            "Modify Biome Colors",
            "Whether to use this mod's biome colors (Currently only affects water)",
            true
    ), CLIENT);

    public static final ModConfigSpec SPEC = CLIENT.build();
}
