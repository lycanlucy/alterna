package io.github.lycanlucy.alterna.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class AlternaServerConfig extends AlternaAbstractConfig {
    public static BooleanConfig CONCH_SHELL_MSG_CLIENT;
    public static BooleanConfig CONCH_SHELL_MSG_SERVER;
    public static BooleanConfig CONCH_SHELL_MSG_NAMED;

    static {
        SERVER.push("conchShellMessage");
        CONCH_SHELL_MSG_CLIENT = define(new BooleanConfig(
                "conchShellMsgClient",
                "Announce to User",
                "Whether to display the message to the player that changed the weather",
                true
        ), SERVER);

        CONCH_SHELL_MSG_SERVER = define(new BooleanConfig(
                "conchShellMsgServer",
                "Announce to Others",
                "Whether to display the message to every other player",
                true
        ), SERVER);

        CONCH_SHELL_MSG_NAMED = define(new BooleanConfig(
                "conchShellMsgNamed",
                "Include Username",
                "Whether to include the player's username in the message",
                true
        ), SERVER);
        SERVER.pop();
    }

    public static final ModConfigSpec SPEC = SERVER.build();
}
