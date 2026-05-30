package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.ConchShellEffect;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import io.github.lycanlucy.alterna.util.AdvancementNames;
import io.github.lycanlucy.alterna.util.ConfigHelper;
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
        add("advancements.adventure.very_very_frightening.description", "Strike a Creeper with lightning");
        AlternaItems.TRANSLATIONS.forEach(this::addItem);
        AlternaEntities.TRANSLATIONS.forEach(this::addEntityType);
        AlternaMobEffects.TRANSLATIONS.forEach(this::addEffect);
        AlternaInstruments.TRANSLATIONS.forEach(this::addInstrument);

        addAdvancement(AdvancementNames.NEEDS_A_TOUCH_UP, "Needs a Touch Up", "Obtain a Sunken Trident");
        addSubtitle("event.mob_effect.lord_of_the_skies", "Conduit's power absorbed");
        addSubtitle("item.conch_shell.play", "Conch Shell plays");

        addConfig(ConfigHelper.MODIFY_BIOME_COLORS_ID, "Modify Biome Colors");
        addConfigTooltip(ConfigHelper.MODIFY_BIOME_COLORS_ID, ConfigHelper.MODIFY_BIOME_COLORS_TOOLTIP);

        addConfig(ConfigHelper.CONCH_SHELL_MSG_CLIENT_ID, "Conch Shell Client Message");
        addConfigTooltip(ConfigHelper.CONCH_SHELL_MSG_CLIENT_ID, ConfigHelper.CONCH_SHELL_MSG_CLIENT_TOOLTIP);

        addConfig(ConfigHelper.CONCH_SHELL_MSG_SERVER_ID, "Conch Shell Server Message");
        addConfigTooltip(ConfigHelper.CONCH_SHELL_MSG_SERVER_ID, ConfigHelper.CONCH_SHELL_MSG_SERVER_TOOLTIP);

        addConfig(ConfigHelper.CONCH_SHELL_MSG_NAMED_ID, "Conch Shell Named Message");
        addConfigTooltip(ConfigHelper.CONCH_SHELL_MSG_NAMED_ID, ConfigHelper.CONCH_SHELL_MSG_NAMED_TOOLTIP);

        addConchShellMessage(ConchShellEffect.POUR, "%s has brought forth a deluge", "A deluge has been brought forth");
        addConchShellMessage(ConchShellEffect.ROAR, "%s has brought forth a tempest", "A tempest has been brought forth");
        addConchShellMessage(ConchShellEffect.SHINE, "%s has brought forth clear skies", "Clear skies have been brought forth");
    }

    private void addConfig(String key, String name) {
        add("alterna.configuration." + key, name);
    }

    private void addConfigTooltip(String key, String name) {
        addConfig(key + ".tooltip", name);
    }

    private void addAdvancement(String key, String title, String description) {
        add(AdvancementNames.title(key).getString(), title);
        add(AdvancementNames.description(key).getString(), description);
    }

    private void addConchShellMessage(ConchShellEffect key, String withPlayerName, String withoutPlayerName) {
        add(key.getTranslationKey(true), withPlayerName);
        add(key.getTranslationKey(false), withoutPlayerName);
    }

    private void addInstrument(Supplier<? extends Instrument> key, String name) {
        add(Util.makeDescriptionId("instrument", BuiltInRegistries.INSTRUMENT.getKey(key.get())), name);
    }

    private void addSubtitle(String key, String name) {
        add("subtitles.alterna." + key, name);
    }
}
