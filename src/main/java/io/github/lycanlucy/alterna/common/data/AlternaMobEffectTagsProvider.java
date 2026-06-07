package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.tag.AlternaMobEffectTags;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AlternaMobEffectTagsProvider extends TagsProvider<MobEffect> {
    public AlternaMobEffectTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.MOB_EFFECT, lookupProvider, Alterna.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AlternaMobEffectTags.UNCLEARABLE).add(MobEffects.CONDUIT_POWER.getKey(), MobEffects.HERO_OF_THE_VILLAGE.getKey(), AlternaMobEffects.LORD_OF_THE_SKIES.getKey());
    }
}
