package io.github.lycanlucy.alterna;

import eu.midnightdust.lib.config.MidnightConfig;

public class AlternaConfig extends MidnightConfig {
    public static final String CLIENT = "client";
    public static final String COMMON = "common";

    public enum ConchShellMessageDisplay {
        IN_CHAT,
        ABOVE_HOTBAR,
        DO_NOT_DISPLAY
    }

    public enum ConchShellMessageType {
        ANNOUNCE_WITH_NAME,
        ANNOUNCE_WITHOUT_NAME,
        DO_NOT_ANNOUNCE
    }

    @Entry(category = CLIENT)
    @Client
    public static boolean useAlternaBiomeColors = true;

    @Entry(category = CLIENT)
    @Client
    public static ConchShellMessageDisplay conchShellMessageDisplay = ConchShellMessageDisplay.IN_CHAT;

    @Entry(category = COMMON)
    public static ConchShellMessageType conchShellMessageType = ConchShellMessageType.ANNOUNCE_WITH_NAME;
}
