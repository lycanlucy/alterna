package io.github.lycanlucy.alterna.data.client;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AlternaItemModelProvider extends ItemModelProvider {
    public AlternaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(AlternaItems.OAK_ITEM_RACK.get());
        basicItem(AlternaItems.SPRUCE_ITEM_RACK.get());
        basicItem(AlternaItems.BIRCH_ITEM_RACK.get());
        basicItem(AlternaItems.JUNGLE_ITEM_RACK.get());
        basicItem(AlternaItems.ACACIA_ITEM_RACK.get());
        basicItem(AlternaItems.DARK_OAK_ITEM_RACK.get());
        basicItem(AlternaItems.MANGROVE_ITEM_RACK.get());
        basicItem(AlternaItems.CHERRY_ITEM_RACK.get());
        basicItem(AlternaItems.BAMBOO_ITEM_RACK.get());
        basicItem(AlternaItems.CRIMSON_ITEM_RACK.get());
        basicItem(AlternaItems.WARPED_ITEM_RACK.get());
    }
}
