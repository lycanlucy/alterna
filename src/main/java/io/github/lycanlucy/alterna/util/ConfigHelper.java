package io.github.lycanlucy.alterna.util;

public interface ConfigHelper {
    String MODIFY_BIOME_COLORS_ID = "modifyBiomeColors";
    String MODIFY_BIOME_COLORS_TOOLTIP = "Whether to use this mod's biome colors (Currently only affects water)";

    String CONCH_SHELL_MSG_CLIENT_ID = "conchShellMsgClient";
    String CONCH_SHELL_MSG_CLIENT_TOOLTIP = "Whether to display a message to a player that changes the weather with a Conch Shell";

    String CONCH_SHELL_MSG_SERVER_ID = "conchShellMsgServer";
    String CONCH_SHELL_MSG_SERVER_TOOLTIP = "Whether to display a message to other players when someone changes the weather with a Conch Shell";

    String CONCH_SHELL_MSG_NAMED_ID = "conchShellMsgNamed";
    String CONCH_SHELL_MSG_NAMED_TOOLTIP = "Whether to show the name of the player that changed the weather in the messages from the above configs";
}
