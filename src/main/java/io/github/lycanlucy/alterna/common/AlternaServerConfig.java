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
    public final ModConfigSpec.DoubleValue riptideWaterInertia;
    public final ModConfigSpec.BooleanValue schoolingFix;

    private AlternaServerConfig(ModConfigSpec.Builder builder) {
        armorStandArms = builder.comment("Toggles armor stands having arms when placed")
                .translation("alterna.config.armor_stand_arms")
                .define("armor_stand_arms", true);

        kelpFix = builder.comment("Toggles a fix to the kelp feature that prevents it from breaking immediately upon generation")
                .translation("alterna.config.kelp_fix")
                .define("kelp_fix", true);

        riptideWaterInertia = builder.comment("Sets the player's inertia while using a riptide trident in water")
                .translation("alterna.config.riptide_water_inertia")
                .defineInRange("riptide_water_inertia", 0.96, 0.0, 1.0);

        schoolingFix = builder.comment("Toggles a fix to fish randomly stopping")
                .translation("alterna.config.schooling_fix")
                .define("schooling_fix", true);
    }

    public static boolean armorStandArms() {
        return CONFIG.armorStandArms.getAsBoolean();
    }

    public static boolean kelpFix() {
        return CONFIG.kelpFix.getAsBoolean();
    }

    public static double riptideWaterInertia() {
        return CONFIG.riptideWaterInertia.getAsDouble();
    }

    public static boolean schoolingFix() {
        return CONFIG.schoolingFix.getAsBoolean();
    }
}
