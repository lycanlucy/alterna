package io.github.lycanlucy.alterna.common.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

public record WeatherParameters(String name, IntProvider clearTime, IntProvider weatherTime, boolean raining,
                                boolean thundering) {
    public static final WeatherParameters CLEAR = new WeatherParameters("clear", ServerLevel.RAIN_DELAY, ConstantInt.of(0), false, false);
    public static final WeatherParameters RAIN = new WeatherParameters("rain", ConstantInt.of(0), ServerLevel.RAIN_DURATION, true, false);
    public static final WeatherParameters THUNDER = new WeatherParameters("thunder", ConstantInt.of(0), ServerLevel.THUNDER_DURATION, true, true);
}