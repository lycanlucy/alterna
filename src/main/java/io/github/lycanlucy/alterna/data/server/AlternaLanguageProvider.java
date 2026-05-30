package io.github.lycanlucy.alterna.data.server;

import com.mojang.datafixers.util.Pair;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.ConchShellEffect;
import io.github.lycanlucy.alterna.config.AlternaAbstractConfig;
import io.github.lycanlucy.alterna.data.list.AlternaAdvancements;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Instrument;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public class AlternaLanguageProvider extends LanguageProvider {
    public AlternaLanguageProvider(PackOutput output) {
        super(output, Alterna.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("alterna.configuration.title", "Alterna Configuration");
        add("alterna.configuration.section.alterna.client.toml", "Alterna Client Configuration");
        add("alterna.configuration.section.alterna.client.toml.title", "Alterna Client Configuration");
        add("alterna.configuration.section.alterna.server.toml", "Alterna Server Configuration");
        add("alterna.configuration.section.alterna.server.toml.title", "Alterna Server Configuration");
        add("alterna.configuration.conchShellMessage", "Conch Shell Message");
        add("alterna.configuration.conchShellMessage.tooltip", "Configures the message that displays after a player uses a Conch Shell to change the weather");
        add("alterna.configuration.conchShellMessage.button", "Edit Settings");
        add("advancements.adventure.very_very_frightening.description", "Strike a Creeper with lightning");
        AlternaItems.TRANSLATIONS.forEach(this::addItem);
        AlternaEntities.TRANSLATIONS.forEach(this::addEntityType);
        AlternaMobEffects.TRANSLATIONS.forEach(this::addEffect);
        AlternaInstruments.TRANSLATIONS.forEach(this::addInstrument);
        AlternaAdvancements.TRANSLATIONS.forEach(this::addAdvancement);
        AlternaAbstractConfig.CONFIGS.keySet().forEach(this::addConfig);
        addConchShellMessage(ConchShellEffect.POUR, "%s has brought forth a deluge", "A deluge has been brought forth");
        addConchShellMessage(ConchShellEffect.ROAR, "%s has brought forth a tempest", "A tempest has been brought forth");
        addConchShellMessage(ConchShellEffect.SHINE, "%s has brought forth clear skies", "Clear skies have been brought forth");
        addSubtitle("event.mob_effect.lord_of_the_skies", "Conduit's power absorbed");
        addSubtitle("item.conch_shell.play", "Conch Shell plays");
    }

    private void addAdvancement(AlternaAdvancements.AlternaAdvancement advancement, Pair<String, String> name) {
        add(AlternaAdvancements.title(advancement.name()), name.getFirst());
        add(AlternaAdvancements.description(advancement.name()), name.getSecond());
    }

    private void addConfig(AlternaAbstractConfig.BooleanConfig config) {
        add("alterna.configuration." + config.id(), config.name());
        add("alterna.configuration." + config.id() + ".tooltip", config.tooltip());
    }

    private void addConchShellMessage(ConchShellEffect effect, String withPlayerName, String withoutPlayerName) {
        add(effect.getTranslationKey(true), withPlayerName);
        add(effect.getTranslationKey(false), withoutPlayerName);
    }

    private void addInstrument(Supplier<? extends Instrument> key, String name) {
        add(Util.makeDescriptionId("instrument", BuiltInRegistries.INSTRUMENT.getKey(key.get())), name);
    }

    private void addSubtitle(String key, String name) {
        add("subtitles.alterna." + key, name);
    }
}
