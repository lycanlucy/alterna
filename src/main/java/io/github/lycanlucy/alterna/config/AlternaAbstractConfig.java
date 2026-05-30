package io.github.lycanlucy.alterna.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;

public abstract class AlternaAbstractConfig {
    public static final HashMap<BooleanConfig, ModConfigSpec.BooleanValue> CONFIGS = new HashMap<>();
    protected static final ModConfigSpec.Builder CLIENT = new ModConfigSpec.Builder();
    protected static final ModConfigSpec.Builder SERVER = new ModConfigSpec.Builder();

    public static BooleanConfig define(BooleanConfig config, ModConfigSpec.Builder builder) {
        ModConfigSpec.BooleanValue spec = builder.comment(config.tooltip()).define(config.id(), config.defaultValue());
        CONFIGS.put(config, spec);
        return config;
    }

    public static boolean getBoolean(BooleanConfig config) {
        return CONFIGS.get(config).getAsBoolean();
    }

    public record BooleanConfig(String id, String name, String tooltip, boolean defaultValue) {
    }
}
