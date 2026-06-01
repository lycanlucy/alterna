package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import io.github.lycanlucy.alterna.data.bootstrap.AlternaBiomeModifiers;
import io.github.lycanlucy.alterna.data.client.AlternaItemModelProvider;
import io.github.lycanlucy.alterna.data.list.AlternaRegistries;
import io.github.lycanlucy.alterna.data.server.AlternaAdvancementProvider;
import io.github.lycanlucy.alterna.data.client.AlternaLanguageProvider;
import io.github.lycanlucy.alterna.data.server.AlternaDataMapProvider;
import io.github.lycanlucy.alterna.data.server.AlternaLootModifierProvider;
import io.github.lycanlucy.alterna.data.server.AlternaRecipeProvider;
import io.github.lycanlucy.alterna.data.server.loot.AlternaBlockLoot;
import io.github.lycanlucy.alterna.data.server.loot.AlternaChestLoot;
import io.github.lycanlucy.alterna.data.server.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
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

        event.createDatapackRegistryObjects(new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AlternaBiomeModifiers::bootstrap).add(AlternaRegistries.MOB_VARIANT, MobVariant::bootstrap));

        BlockTagsProvider blockTags = generator.addProvider(event.includeServer(), new AlternaBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaInstrumentTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaEntityTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new AlternaDataMapProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(new LootTableProvider.SubProviderEntry(AlternaBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(AlternaChestLoot::new, LootContextParamSets.CHEST)), lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaLootModifierProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new AlternaItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new AlternaLanguageProvider(packOutput));
    }
}
