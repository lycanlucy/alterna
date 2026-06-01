package io.github.lycanlucy.alterna.util;

import io.github.lycanlucy.alterna.data.list.AlternaInstrumentTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public interface CreativeModeTabHelper {
    static void populateFunctionalBlocks(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.FUNCTIONAL_BLOCKS) return;
        before(event, Items.ITEM_FRAME, AlternaItems.OAK_ITEM_RACK);
    }

    static void populateToolsAndUtilities(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.TOOLS_AND_UTILITIES) return;
        event.getParameters().holders().lookup(Registries.INSTRUMENT).ifPresent(instrumentLookup -> instruments(event, instrumentLookup, AlternaItems.CONCH_SHELL.get(), AlternaInstrumentTags.CONCH_SHELLS));
    }

    static void populateCombat(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.COMBAT) return;
        after(event, Items.TRIDENT, AlternaItems.TRIDENT);
        after(event, Items.TRIDENT, AlternaItems.SUNKEN_TRIDENT);
        event.remove(new ItemStack(Items.TRIDENT), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    // Insert a new stack after the referenced item
    private static void after(BuildCreativeModeTabContentsEvent event, ItemLike existingEntry, ItemLike newEntry) {
        event.insertAfter(new ItemStack(existingEntry), new ItemStack(newEntry), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    // Insert a new stack before the referenced item
    private static void before(BuildCreativeModeTabContentsEvent event, ItemLike existingEntry, ItemLike newEntry) {
        event.insertBefore(new ItemStack(existingEntry), new ItemStack(newEntry), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void instruments(BuildCreativeModeTabContentsEvent event, HolderLookup<Instrument> instrumentLookup, Item item, TagKey<Instrument> instrumentTags) {
        instrumentLookup.get(instrumentTags).ifPresent(instruments -> instruments.stream().map(instrument -> InstrumentItem.create(item, instrument)).forEach(itemStack -> event.insertBefore(new ItemStack(Items.MUSIC_DISC_13), itemStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)));
    }
}
