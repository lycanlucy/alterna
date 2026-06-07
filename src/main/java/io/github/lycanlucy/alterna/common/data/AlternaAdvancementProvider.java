package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.advancement.BoostedWithGliderTrigger;
import io.github.lycanlucy.alterna.common.advancement.SpawnedPhantomsTrigger;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AlternaAdvancementProvider extends AdvancementProvider {
    public AlternaAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    private static final class Generator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("adventure/root"))
                    .display(AlternaItems.SUNKEN_TRIDENT, Component.translatable("advancements.alterna.adventure.needs_a_touch_up.title"), Component.translatable("advancements.alterna.adventure.needs_a_touch_up.description"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("has_sunken_trident", InventoryChangeTrigger.TriggerInstance.hasItems(AlternaItems.SUNKEN_TRIDENT))
                    .save(saver, Alterna.id("adventure/needs_a_touch_up"), existingFileHelper);
            
            AdvancementHolder iMustBeDreaming = Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("adventure/root"))
                    .display(Items.PHANTOM_MEMBRANE, Component.translatable("advancements.alterna.adventure.i_must_be_dreaming.title"), Component.translatable("advancements.alterna.adventure.i_must_be_dreaming.description"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("spawned_phantoms", SpawnedPhantomsTrigger.TriggerInstance.spawnedPhantoms())
                    .save(saver, Alterna.id("adventure/i_must_be_dreaming"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(iMustBeDreaming)
                    .display(AlternaItems.GLIDER, Component.translatable("advancements.alterna.adventure.full_of_hot_air.title"), Component.translatable("advancements.alterna.adventure.full_of_hot_air.description"), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("boosted_with_glider", BoostedWithGliderTrigger.TriggerInstance.boostedWithGlider())
                    .save(saver, Alterna.id("adventure/full_of_hot_air"), existingFileHelper);
        }
    }
}
