package io.github.lycanlucy.alterna.common.item;

import com.google.common.collect.Maps;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Instrument;

import java.util.HashMap;

public record ConchShellEffect(String name, IntProvider clearTime, IntProvider weatherTime, boolean raining,
                               boolean thundering) {
    public static final ConchShellEffect POUR = new ConchShellEffect("pour", ConstantInt.of(0), ServerLevel.RAIN_DURATION, true, false);
    public static final ConchShellEffect ROAR = new ConchShellEffect("roar", ConstantInt.of(0), ServerLevel.THUNDER_DURATION, true, true);
    public static final ConchShellEffect SHINE = new ConchShellEffect("shine", ServerLevel.RAIN_DELAY, ConstantInt.of(0), false, false);

    public static final HashMap<Holder<Instrument>, ConchShellEffect> INSTRUMENT_TO_EFFECT_MAP = Util.make(Maps.newHashMap(), map -> {
        map.put(AlternaInstruments.POUR_CONCH_SHELL, POUR);
        map.put(AlternaInstruments.ROAR_CONCH_SHELL, ROAR);
        map.put(AlternaInstruments.SHINE_CONCH_SHELL, SHINE);
    });

    public Component getTranslationComponent(Entity user) {
        return Component.translatable(getTranslationKey(true), user.getDisplayName());
    }

    public Component getTranslationComponent() {
        return Component.translatable(getTranslationKey(false));
    }

    public String getTranslationKey(boolean includeUserName) {
        if (includeUserName) {
            return "item.alterna.conch_shell.message." + name + ".username";
        }
        return "item.alterna.conch_shell.message." + name;
    }
}
