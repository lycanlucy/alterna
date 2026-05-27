package io.github.lycanlucy.alterna.util;

import com.google.common.collect.Maps;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Instrument;

import java.util.HashMap;

public record ConchShellEffect(IntProvider clearTime, IntProvider weatherTime, boolean raining, boolean thundering) {
    public static final HashMap<Holder<Instrument>, ConchShellEffect> INSTRUMENT_TO_EFFECT_MAP = Util.make(Maps.newHashMap(), map -> {
        map.put(AlternaInstruments.POUR_CONCH_SHELL, new ConchShellEffect(ConstantInt.of(0), ServerLevel.RAIN_DURATION, true, false));
        map.put(AlternaInstruments.ROAR_CONCH_SHELL, new ConchShellEffect(ConstantInt.of(0), ServerLevel.THUNDER_DURATION, true, true));
        map.put(AlternaInstruments.SHINE_CONCH_SHELL, new ConchShellEffect(ServerLevel.RAIN_DELAY, ConstantInt.of(0), false, false));
    });
}
