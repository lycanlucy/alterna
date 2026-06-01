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
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return AlternaBlocks.BLOCKS.getEntries().stream().map(e -> (Block) e.value()).toList();
    }
}
