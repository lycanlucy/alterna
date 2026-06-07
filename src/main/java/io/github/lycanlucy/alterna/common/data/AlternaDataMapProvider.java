package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.common.tag.AlternaItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class AlternaDataMapProvider extends DataMapProvider {
    public AlternaDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.FURNACE_FUELS).add(AlternaItemTags.ITEM_RACKS, new FurnaceFuel(300), false).remove(ItemTags.NON_FLAMMABLE_WOOD);
    }
}