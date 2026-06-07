package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.tag.AlternaBlockTags;
import io.github.lycanlucy.alterna.common.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class AlternaItemTagsProvider extends ItemTagsProvider {
    public AlternaItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(AlternaBlockTags.ITEM_RACKS, AlternaItemTags.ITEM_RACKS);

        tag(AlternaItemTags.TRIDENTS).add(Items.TRIDENT, AlternaItems.SUNKEN_TRIDENT.get());
        tag(AlternaItemTags.TRIDENT_REPAIR_MATERIALS).add(Items.PRISMARINE_SHARD);

        tag(Tags.Items.TOOLS_SPEAR).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.MELEE_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);
        tag(Tags.Items.RANGED_WEAPON_TOOLS).addTag(AlternaItemTags.TRIDENTS);

        tag(ItemTags.BREAKS_DECORATED_POTS).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.TRIDENT_ENCHANTABLE).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.DURABILITY_ENCHANTABLE).addTag(AlternaItemTags.TRIDENTS);
        tag(ItemTags.DYEABLE).add(AlternaItems.GLIDER.get());
        tag(ItemTags.NON_FLAMMABLE_WOOD).add(AlternaItems.CRIMSON_ITEM_RACK.get(), AlternaItems.WARPED_ITEM_RACK.get());
    }
}