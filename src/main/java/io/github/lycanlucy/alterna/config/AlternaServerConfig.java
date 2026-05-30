package io.github.lycanlucy.alterna.config;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.TranslatableEnum;
import org.jetbrains.annotations.NotNull;

public class AlternaServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<ConchShellMessageType> CONCH_SHELL_MESSAGE_TYPE = BUILDER
            .comment("Whether and how to announce to the server when a player alters the weather with a Conch Shell.")
            .defineEnum("conchShellMessageType", ConchShellMessageType.ANNOUNCE_TO_OTHER_PLAYERS);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public enum ConchShellMessageType implements TranslatableEnum {
        ANNOUNCE_TO_OTHER_PLAYERS("announceToOtherPlayers"),
        ANNOUNCE_TO_OTHER_PLAYERS_ANONYMOUSLY("announceToOtherPlayersAnonymously"),
        NONE("none");

        private final String name;

        ConchShellMessageType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull Component getTranslatedName() {
            return Component.translatable("alterna.configuration.conchShellMessageType.option." + this.name);
        }
    }
}
