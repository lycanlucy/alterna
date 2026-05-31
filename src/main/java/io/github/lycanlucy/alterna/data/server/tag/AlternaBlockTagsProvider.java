package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AlternaBlockTagsProvider extends BlockTagsProvider {
    public AlternaBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
