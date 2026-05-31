package io.github.lycanlucy.alterna.common.item;

import io.github.lycanlucy.alterna.config.AlternaAbstractConfig;
import io.github.lycanlucy.alterna.config.AlternaServerConfig;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ConchShellItem extends InstrumentItem {
    public ConchShellItem(Properties properties, TagKey<Instrument> instruments) {
        super(properties, instruments);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        if (!(level instanceof ServerLevel serverLevel) || !livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
            return;
        }

        Optional<Holder<Instrument>> instrument = this.getInstrument(stack);
        instrument.ifPresent(holder -> {
            if (ConchShellEffect.INSTRUMENT_TO_EFFECT_MAP.containsKey(holder)) {
                performWeatherChange(livingEntity, serverLevel, serverLevel.getRandom(), ConchShellEffect.INSTRUMENT_TO_EFFECT_MAP.get(holder));
            }
        });
    }

    protected static void performWeatherChange(LivingEntity livingEntity, ServerLevel serverLevel, RandomSource random, ConchShellEffect effect) {
        serverLevel.setWeatherParameters(effect.clearTime().sample(random), effect.weatherTime().sample(random), effect.raining(), effect.thundering());

        livingEntity.addEffect(new MobEffectInstance(AlternaMobEffects.LORD_OF_THE_SKIES, 24000, 0, true, true));
        livingEntity.removeEffect(MobEffects.CONDUIT_POWER);

        for (ServerPlayer player : serverLevel.players()) {
            Component message = AlternaAbstractConfig.getBoolean(AlternaServerConfig.CONCH_SHELL_MSG_NAMED) ? effect.getTranslationComponent(player) : effect.getTranslationComponent();
            if (player == livingEntity && AlternaAbstractConfig.getBoolean(AlternaServerConfig.CONCH_SHELL_MSG_CLIENT)) {
                player.displayClientMessage(message, false);
            } else if (player != livingEntity && AlternaAbstractConfig.getBoolean(AlternaServerConfig.CONCH_SHELL_MSG_SERVER)) {
                player.displayClientMessage(message, false);
            }
        }
    }
}
