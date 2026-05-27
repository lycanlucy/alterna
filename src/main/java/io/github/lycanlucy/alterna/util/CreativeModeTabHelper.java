package io.github.lycanlucy.alterna.util;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public interface CreativeModeTabHelper {
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
}
