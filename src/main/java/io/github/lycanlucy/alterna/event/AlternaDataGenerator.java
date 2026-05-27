package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.bootstrap.AlternaBiomeModifiers;
import io.github.lycanlucy.alterna.data.server.*;
import io.github.lycanlucy.alterna.data.server.loot.AlternaChestLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        BlockTagsProvider blockTags = generator.addProvider(event.includeServer(), new AlternaBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaInstrumentTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaEntityTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new AlternaRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AlternaBiomeModifiers::bootstrap), Set.of(Alterna.MOD_ID)));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(AlternaChestLoot::new, LootContextParamSets.CHEST)), lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaLootModifierProvider(packOutput, lookupProvider));
    }
}
