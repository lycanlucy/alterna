package io.github.lycanlucy.alterna.common;

import io.github.lycanlucy.alterna.common.tag.AlternaInstrumentTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class AlternaCreativeContents {
    static void populateFunctionalBlocks(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.FUNCTIONAL_BLOCKS) return;
        before(event, Items.ITEM_FRAME, AlternaItems.OAK_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.SPRUCE_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.BIRCH_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.JUNGLE_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.ACACIA_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.DARK_OAK_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.MANGROVE_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.CHERRY_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.BAMBOO_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.CRIMSON_ITEM_RACK);
        before(event, Items.ITEM_FRAME, AlternaItems.WARPED_ITEM_RACK);
    }

    static void populateToolsAndUtilities(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.TOOLS_AND_UTILITIES) return;
        before(event, Items.ELYTRA, AlternaItems.GLIDER);
        before(event, Items.TADPOLE_BUCKET, AlternaItems.BABY_TURTLE_BUCKET);
        after(event, Items.MUSIC_DISC_OTHERSIDE, AlternaItems.MUSIC_DISC_CREEP);
        event.getParameters().holders().lookup(Registries.INSTRUMENT).ifPresent(instrumentLookup -> instruments(event, instrumentLookup, AlternaItems.CONCH_SHELL.get(), AlternaInstrumentTags.CONCH_SHELLS));
    }

    static void populateCombat(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.COMBAT) return;
        before(event, Items.TRIDENT, AlternaItems.SUNKEN_TRIDENT);
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
