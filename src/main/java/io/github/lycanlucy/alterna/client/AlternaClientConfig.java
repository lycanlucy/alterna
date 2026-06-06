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

    public final ModConfigSpec.BooleanValue redesignSalmon;
    public boolean wasRedesignSalmonEnabled;

    private AlternaClientConfig(ModConfigSpec.Builder builder) {
        redesignSalmon = builder.comment("Toggles the Salmon redesign and variants")
                .translation("alterna.config.redesign_salmon")
                .define("redesign_salmon", true);
    }

    public static boolean redesignSalmon() {
        return CONFIG.redesignSalmon.getAsBoolean();
    }
}
