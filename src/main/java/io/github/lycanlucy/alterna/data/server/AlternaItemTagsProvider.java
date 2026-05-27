package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.server.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AlternaItemTagsProvider extends ItemTagsProvider {
    public AlternaItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(AlternaItemTags.TRIDENTS).add(AlternaItems.TRIDENT.get(), AlternaItems.SUNKEN_TRIDENT.get());

        tag(Tags.Items.TOOLS_SPEAR).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.RANGED_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);

        tag(ItemTags.BREAKS_DECORATED_POTS).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.TRIDENT_ENCHANTABLE).addTag(AlternaItemTags.TRIDENTS);
    }
}
