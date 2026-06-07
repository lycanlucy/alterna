package io.github.lycanlucy.alterna.common;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AlternaServerConfig {
    public static final AlternaServerConfig CONFIG;
    public static final ModConfigSpec SPEC;

    static {
        Pair<AlternaServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(AlternaServerConfig::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public final ModConfigSpec.BooleanValue armorStandArms;
    public final ModConfigSpec.BooleanValue kelpFix;

    private AlternaServerConfig(ModConfigSpec.Builder builder) {
        armorStandArms = builder.comment("Toggles armor stands having arms when placed")
                .translation("alterna.config.armor_stand_arms")
                .define("armor_stand_arms", true);

        kelpFix = builder.comment("If true, Kelp won't generate on top of suspended gravity-affected blocks, preventing them from breaking immediately upon generation")
                .translation("alterna.config.kelp_fix")
                .define("kelp_fix", true);
    }

    public static boolean armorStandArms() {
        return CONFIG.armorStandArms.getAsBoolean();
    }

    public static boolean kelpFix() {
        return CONFIG.kelpFix.getAsBoolean();
    }
}
