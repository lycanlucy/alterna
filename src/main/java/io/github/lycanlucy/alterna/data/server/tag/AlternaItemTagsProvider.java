package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaBlockTags;
import io.github.lycanlucy.alterna.data.list.AlternaItemTags;
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

import static io.github.lycanlucy.alterna.registry.AlternaItems.SUNKEN_TRIDENT;
import static io.github.lycanlucy.alterna.registry.AlternaItems.TRIDENT;

public class AlternaItemTagsProvider extends ItemTagsProvider {
    public AlternaItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(AlternaBlockTags.ITEM_RACKS, AlternaItemTags.ITEM_RACKS);

        tag(AlternaItemTags.TRIDENTS).add(TRIDENT.get(), SUNKEN_TRIDENT.get());

        tag(Tags.Items.TOOLS_SPEAR).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.RANGED_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);

        tag(ItemTags.BREAKS_DECORATED_POTS).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.TRIDENT_ENCHANTABLE).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.NON_FLAMMABLE_WOOD).add(AlternaItems.CRIMSON_ITEM_RACK.get(), AlternaItems.WARPED_ITEM_RACK.get());
    }
}
