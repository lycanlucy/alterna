package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaInstrumentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.InstrumentTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static io.github.lycanlucy.alterna.registry.AlternaInstruments.*;

public class AlternaInstrumentTagsProvider extends InstrumentTagsProvider {
    public AlternaInstrumentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(AlternaInstrumentTags.CONCH_SHELLS).add(POUR_CONCH_SHELL.getKey(), ROAR_CONCH_SHELL.getKey(), SHINE_CONCH_SHELL.getKey());
    }
}
