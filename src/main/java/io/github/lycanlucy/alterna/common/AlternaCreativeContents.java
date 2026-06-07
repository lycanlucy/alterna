package io.github.lycanlucy.alterna.common;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class AlternaCreativeContents {
    static void populateToolsAndUtilities(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.TOOLS_AND_UTILITIES) return;
        before(event, Items.ELYTRA, AlternaItems.GLIDER);
        before(event, Items.TADPOLE_BUCKET, AlternaItems.BABY_TURTLE_BUCKET);
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
