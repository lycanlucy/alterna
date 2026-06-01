package io.github.lycanlucy.alterna.data.client;

import com.mojang.datafixers.util.Pair;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.ConchShellEffect;
import io.github.lycanlucy.alterna.data.list.AlternaAdvancements;
import io.github.lycanlucy.alterna.registry.*;
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
        add("advancements.adventure.very_very_frightening.description", "Strike a Creeper with lightning");
        add("alterna.midnightconfig.title", "Alterna Configuration");
        add("alterna.midnightconfig.category.client", "Client");
        add("alterna.midnightconfig.category.common", "Common");
        add("alterna.midnightconfig.useAlternaBiomeColors", "Use Alterna Biome Colors");
        add("alterna.midnightconfig.useAlternaBiomeColors.tooltip", "Whether to apply this mod's biome color modifications. \nCan be turned off in case it overrides a different mod's color changes. \nNOTE: Requires rejoining the world to work properly");
        add("alterna.midnightconfig.conchShellMessageDisplay", "Conch Shell Message Display");
        add("alterna.midnightconfig.conchShellMessageDisplay.tooltip", "Controls where and if to display the announcement for when a player alters the weather with a Conch Shell. \nOnly works if the server has the message enabled.");
        add("alterna.midnightconfig.enum.ConchShellMessageDisplay.IN_CHAT", "Display in Chat");
        add("alterna.midnightconfig.enum.ConchShellMessageDisplay.ABOVE_HOTBAR", "Display Above Hotbar");
        add("alterna.midnightconfig.enum.ConchShellMessageDisplay.DO_NOT_DISPLAY", "Do Not Display");
        add("alterna.midnightconfig.conchShellMessageType", "Conch Shell Message Type");
        add("alterna.midnightconfig.conchShellMessageType.tooltip", "Controls if the announcement for when a player alters the weather with a Conch Shell should include the player's name, or if it should be announced at all.");
        add("alterna.midnightconfig.enum.ConchShellMessageType.ANNOUNCE_WITH_NAME", "Announce With Name");
        add("alterna.midnightconfig.enum.ConchShellMessageType.ANNOUNCE_WITHOUT_NAME", "Announce Without Name");
        add("alterna.midnightconfig.enum.ConchShellMessageType.DO_NOT_ANNOUNCE", "Do Not Announce");
        AlternaBlocks.TRANSLATIONS.forEach(this::addBlock);
        AlternaItems.TRANSLATIONS.forEach(this::addItem);
        AlternaEntities.TRANSLATIONS.forEach(this::addEntityType);
        AlternaMobEffects.TRANSLATIONS.forEach(this::addEffect);
        AlternaInstruments.TRANSLATIONS.forEach(this::addInstrument);
        AlternaAdvancements.TRANSLATIONS.forEach(this::addAdvancement);
        addConchShellMessage(ConchShellEffect.POUR, "%s has brought forth a deluge", "A deluge has been brought forth");
        addConchShellMessage(ConchShellEffect.ROAR, "%s has brought forth a tempest", "A tempest has been brought forth");
        addConchShellMessage(ConchShellEffect.SHINE, "%s has brought forth clear skies", "Clear skies have been brought forth");
        addSubtitle("event.mob_effect.lord_of_the_skies", "Conduit's power absorbed");
        addSubtitle("item.conch_shell.play", "Conch Shell plays");
        addSubtitle("block.item_rack.add_item", "Item Rack fills");
        addSubtitle("block.item_rack.remove_item", "Item Rack empties");
        addSubtitle("block.item_rack.rotate_item", "Item Rack clicks");
    }

    private void addAdvancement(AlternaAdvancements.AlternaAdvancement advancement, Pair<String, String> name) {
        add(AlternaAdvancements.title(advancement.name()), name.getFirst());
        add(AlternaAdvancements.description(advancement.name()), name.getSecond());
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
