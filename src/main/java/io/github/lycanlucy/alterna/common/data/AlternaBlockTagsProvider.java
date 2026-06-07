package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.tag.AlternaBlockTags;
import io.github.lycanlucy.alterna.registry.AlternaBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AlternaBlockTagsProvider extends BlockTagsProvider {
    public AlternaBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AlternaBlockTags.ITEM_RACKS).add(AlternaBlocks.OAK_ITEM_RACK.get(), AlternaBlocks.SPRUCE_ITEM_RACK.get(), AlternaBlocks.BIRCH_ITEM_RACK.get(), AlternaBlocks.JUNGLE_ITEM_RACK.get(), AlternaBlocks.ACACIA_ITEM_RACK.get(), AlternaBlocks.DARK_OAK_ITEM_RACK.get(), AlternaBlocks.MANGROVE_ITEM_RACK.get(), AlternaBlocks.CHERRY_ITEM_RACK.get(), AlternaBlocks.BAMBOO_ITEM_RACK.get(), AlternaBlocks.CRIMSON_ITEM_RACK.get(), AlternaBlocks.WARPED_ITEM_RACK.get());

        tag(BlockTags.MINEABLE_WITH_AXE).addTag(AlternaBlockTags.ITEM_RACKS);
    }
}
