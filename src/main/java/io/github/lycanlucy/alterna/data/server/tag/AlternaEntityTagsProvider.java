package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static io.github.lycanlucy.alterna.registry.AlternaEntities.TRIDENT;

public class AlternaEntityTagsProvider extends EntityTypeTagsProvider {
    public AlternaEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(EntityTypeTags.IMPACT_PROJECTILES).add(TRIDENT.get());
    }
}
