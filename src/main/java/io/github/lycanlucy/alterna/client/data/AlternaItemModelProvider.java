package io.github.lycanlucy.alterna.client.data;

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
        basicItem(AlternaItems.BABY_TURTLE_BUCKET.get());
    }
}
