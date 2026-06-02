package io.github.lycanlucy.alterna.data.server.loot;

import io.github.lycanlucy.alterna.registry.AlternaBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AlternaBlockLoot extends BlockLootSubProvider {
    public AlternaBlockLoot(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected void generate() {
        dropSelf(AlternaBlocks.OAK_ITEM_RACK.get());
        dropSelf(AlternaBlocks.SPRUCE_ITEM_RACK.get());
        dropSelf(AlternaBlocks.BIRCH_ITEM_RACK.get());
        dropSelf(AlternaBlocks.JUNGLE_ITEM_RACK.get());
        dropSelf(AlternaBlocks.ACACIA_ITEM_RACK.get());
        dropSelf(AlternaBlocks.DARK_OAK_ITEM_RACK.get());
        dropSelf(AlternaBlocks.MANGROVE_ITEM_RACK.get());
        dropSelf(AlternaBlocks.CHERRY_ITEM_RACK.get());
        dropSelf(AlternaBlocks.BAMBOO_ITEM_RACK.get());
        dropSelf(AlternaBlocks.CRIMSON_ITEM_RACK.get());
        dropSelf(AlternaBlocks.WARPED_ITEM_RACK.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return AlternaBlocks.BLOCKS.getEntries().stream().map(e -> (Block) e.value()).toList();
    }
}
