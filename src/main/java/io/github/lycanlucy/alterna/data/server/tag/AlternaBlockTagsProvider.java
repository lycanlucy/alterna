package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static io.github.lycanlucy.alterna.registry.AlternaBlocks.*;

public class AlternaBlockTagsProvider extends BlockTagsProvider {
    public AlternaBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(AlternaBlockTags.ITEM_RACKS).add(OAK_ITEM_RACK.get(), SPRUCE_ITEM_RACK.get(), BIRCH_ITEM_RACK.get(), JUNGLE_ITEM_RACK.get(), ACACIA_ITEM_RACK.get(), DARK_OAK_ITEM_RACK.get(), MANGROVE_ITEM_RACK.get(), CHERRY_ITEM_RACK.get(), BAMBOO_ITEM_RACK.get(), CRIMSON_ITEM_RACK.get(), WARPED_ITEM_RACK.get());

        tag(BlockTags.MINEABLE_WITH_AXE).addTag(AlternaBlockTags.ITEM_RACKS);
    }
}
