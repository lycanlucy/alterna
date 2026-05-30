package io.github.lycanlucy.alterna.util;

import net.minecraft.network.chat.Component;

public interface AdvancementNames {
    String NEEDS_A_TOUCH_UP = "adventure.needs_a_touch_up";

    static Component title(String name) {
        return Component.translatable("advancements.alterna." + name + ".title");
    }

    static Component description(String name) {
        return Component.translatable("advancements.alterna." + name + ".description");
    }
}
