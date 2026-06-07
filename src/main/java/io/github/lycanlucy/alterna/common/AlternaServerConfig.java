package io.github.lycanlucy.alterna.common;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.TranslatableEnum;
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
    public final ModConfigSpec.EnumValue<ConchShellMessage> conchShellMessage;
    public final ModConfigSpec.BooleanValue insomniaFix;
    public final ModConfigSpec.BooleanValue kelpFix;
    public final ModConfigSpec.BooleanValue opportunisticPhantoms;
    public final ModConfigSpec.DoubleValue riptideWaterInertia;
    public final ModConfigSpec.BooleanValue schoolingFix;

    private AlternaServerConfig(ModConfigSpec.Builder builder) {
        armorStandArms = builder.comment("Toggles armor stands having arms when placed")
                .translation("alterna.config.armor_stand_arms")
                .define("armor_stand_arms", true);

        conchShellMessage = builder.comment("Whether to announce when a player uses a conch shell to change the weather")
                .translation("alterna.config.conch_shell_message")
                .defineEnum("conch_shell_message", ConchShellMessage.ANNOUNCE_WITH_CAUSE);

        insomniaFix = builder.comment("Toggles if the sleep timer won't reset unless the player skips the night or dies")
                .translation("alterna.config.insomnia_fix")
                .define("insomnia_fix", true);

        kelpFix = builder.comment("Toggles a fix to the kelp feature that prevents it from breaking immediately upon generation")
                .translation("alterna.config.kelp_fix")
                .define("kelp_fix", true);

        opportunisticPhantoms = builder.comment("Toggles if phantoms should only attack players at or below half of their max health")
                .translation("alterna.config.opportunistic_phantoms")
                .define("opportunistic_phantoms", true);

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

    public static ConchShellMessage conchShellMessage() {
        return CONFIG.conchShellMessage.get();
    }

    public static boolean insomniaFix() {
        return CONFIG.insomniaFix.getAsBoolean();
    }

    public static boolean kelpFix() {
        return CONFIG.kelpFix.getAsBoolean();
    }

    public static boolean opportunisticPhantoms() {
        return CONFIG.opportunisticPhantoms.getAsBoolean();
    }

    public static double riptideWaterInertia() {
        return CONFIG.riptideWaterInertia.getAsDouble();
    }

    public static boolean schoolingFix() {
        return CONFIG.schoolingFix.getAsBoolean();
    }

    public enum ConchShellMessage implements TranslatableEnum {
        ANNOUNCE_WITH_CAUSE("alterna.config.announce_with_cause"),
        ANNOUNCE_WITHOUT_CAUSE("alterna.config.announce_without_cause"),
        OFF("options.off");

        private final String name;

        ConchShellMessage(String name) {
            this.name = name;
        }

        public boolean hasCause() {
            return this == ANNOUNCE_WITH_CAUSE;
        }

        public boolean disabled() {
            return this == OFF;
        }

        @Override
        public Component getTranslatedName() {
            return Component.translatable(name);
        }
    }

}
