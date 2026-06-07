package io.github.lycanlucy.alterna.client;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AlternaClientConfig {
    public static final AlternaClientConfig CONFIG;
    public static final ModConfigSpec SPEC;

    static {
        Pair<AlternaClientConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(AlternaClientConfig::new);

        CONFIG = pair.getLeft();
        SPEC = pair.getRight();
    }

    public final ModConfigSpec.DoubleValue aquariumOpacity;
    public final ModConfigSpec.BooleanValue immersiveFish;
    public final ModConfigSpec.BooleanValue modifyBiomeColors;
    public final ModConfigSpec.BooleanValue redesignSalmon;
    public double previousAquariumOpacity;
    public boolean previousModifyBiomeColors;
    public boolean previousRedesignSalmon;

    private AlternaClientConfig(ModConfigSpec.Builder builder) {
        aquariumOpacity = builder.comment("Sets how opaque water should be when seen behind transparent blocks such as glass")
                .translation("alterna.config.aquarium_opacity")
                .defineInRange("aquarium_opacity", 0.7, 0.0, 1.0);

        immersiveFish = builder.comment("Toggles the mod's fish and tadpole animations")
                .translation("alterna.config.immersive_fish")
                .define("immersive_fish", true);

        modifyBiomeColors = builder.comment("Toggles the mod's biome color modifications")
                .translation("alterna.config.modify_biome_colors")
                .define("modify_biome_colors", true);

        redesignSalmon = builder.comment("Toggles the salmon redesign and variants")
                .translation("alterna.config.redesign_salmon")
                .define("redesign_salmon", true);
    }

    public static double aquariumOpacity() {
        return CONFIG.aquariumOpacity.getAsDouble();
    }

    public static boolean immersiveFish() {
        return CONFIG.immersiveFish.getAsBoolean();
    }

    public static boolean modifyBiomeColors() {
        return CONFIG.modifyBiomeColors.getAsBoolean();
    }

    public static boolean redesignSalmon() {
        return CONFIG.redesignSalmon.getAsBoolean();
    }
}
