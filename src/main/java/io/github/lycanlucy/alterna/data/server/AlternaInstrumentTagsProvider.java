package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaInstrumentTags;
import io.github.lycanlucy.alterna.registry.AlternaInstruments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.InstrumentTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AlternaInstrumentTagsProvider extends InstrumentTagsProvider {
    public AlternaInstrumentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(AlternaInstrumentTags.CONCH_SHELLS).add(AlternaInstruments.POUR_CONCH_SHELL.getKey(), AlternaInstruments.ROAR_CONCH_SHELL.getKey(), AlternaInstruments.SHINE_CONCH_SHELL.getKey());
    }
}
