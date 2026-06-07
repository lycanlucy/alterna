package io.github.lycanlucy.alterna.common.item;

import com.google.common.collect.Maps;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Optional;

public class ConchShellItem extends InstrumentItem {
    public static final HashMap<Holder<Instrument>, WeatherParameters> WEATHER_PARAMETERS_FOR_INSTRUMENT = Util.make(Maps.newHashMap(), map -> {
        map.put(AlternaInstruments.POUR_CONCH_SHELL, WeatherParameters.RAIN);
        map.put(AlternaInstruments.ROAR_CONCH_SHELL, WeatherParameters.THUNDER);
        map.put(AlternaInstruments.SHINE_CONCH_SHELL, WeatherParameters.CLEAR);
    });

    public ConchShellItem(Properties properties, TagKey<Instrument> instruments) {
        super(properties, instruments);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        if (!livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
            return;
        }

        Optional<Holder<Instrument>> instrument = this.getInstrument(stack);
        instrument.ifPresent(holder -> {
            if (WEATHER_PARAMETERS_FOR_INSTRUMENT.containsKey(holder)) {
                this.performWeatherChange(livingEntity, level, level.getRandom(), WEATHER_PARAMETERS_FOR_INSTRUMENT.get(holder));
            }
        });
    }

    protected void performWeatherChange(LivingEntity cause, Level level, RandomSource random, WeatherParameters parameters) {
        if (level instanceof ServerLevel) {
            ((ServerLevel) level).setWeatherParameters(parameters.clearTime().sample(random), parameters.weatherTime().sample(random), parameters.raining(), parameters.thundering());
        }
        MobEffectInstance effect = new MobEffectInstance(AlternaMobEffects.LORD_OF_THE_SKIES, 24000, 0, true, true);
        cause.addEffect(effect);
        cause.removeEffect(MobEffects.CONDUIT_POWER);

        if (!level.isClientSide() || AlternaServerConfig.conchShellMessage().disabled())
            return;
        for (Player player : level.players()) {
            if (AlternaClientConfig.conchShellMessageDisplay().disabled())
                return;
            Component message;
            if (AlternaServerConfig.conchShellMessage().hasCause()) {
                message = getWeatherChangeMessage(parameters, cause);
            } else {
                message = getWeatherChangeMessage(parameters);
            }
            player.displayClientMessage(message, AlternaClientConfig.conchShellMessageDisplay().actionBar());
        }
    }

    public Component getWeatherChangeMessage(WeatherParameters parameters, Entity cause) {
        return Component.translatable(getWeatherChangeMessage(parameters, true), cause.getDisplayName());
    }

    public Component getWeatherChangeMessage(WeatherParameters parameters) {
        return Component.translatable(getWeatherChangeMessage(parameters, false));
    }

    public String getWeatherChangeMessage(WeatherParameters parameters, boolean includeCauseName) {
        String message = "item.alterna.conch_shell.weather_change";
        return includeCauseName ? message + ".cause" + "." + parameters.name() : message + "." + parameters.name();
    }
}
