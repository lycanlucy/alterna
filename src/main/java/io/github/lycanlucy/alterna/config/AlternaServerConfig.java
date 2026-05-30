package io.github.lycanlucy.alterna.config;

import io.github.lycanlucy.alterna.util.ConfigHelper;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CONCH_SHELL_MSG_CLIENT = BUILDER
            .comment(ConfigHelper.CONCH_SHELL_MSG_CLIENT_TOOLTIP)
            .define(ConfigHelper.CONCH_SHELL_MSG_CLIENT_ID, false);

    public static final ModConfigSpec.BooleanValue CONCH_SHELL_MSG_SERVER = BUILDER
            .comment(ConfigHelper.CONCH_SHELL_MSG_SERVER_TOOLTIP)
            .define(ConfigHelper.CONCH_SHELL_MSG_SERVER_ID, true);

    public static final ModConfigSpec.BooleanValue CONCH_SHELL_MSG_NAMED = BUILDER
            .comment(ConfigHelper.CONCH_SHELL_MSG_NAMED_TOOLTIP)
            .define(ConfigHelper.CONCH_SHELL_MSG_NAMED_ID, true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
