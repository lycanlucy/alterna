package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AlternaAdvancementProvider extends AdvancementProvider {
    public AlternaAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    private static final class Generator implements AdvancementProvider.AdvancementGenerator {

        public static final String NEEDS_A_TOUCH_UP = "adventure.needs_a_touch_up";

        @Override
        public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
            Advancement.Builder.advancement()
                    .parent(AdvancementSubProvider.createPlaceholder("minecraft:adventure/root"))
                    .display(AlternaItems.SUNKEN_TRIDENT, title(NEEDS_A_TOUCH_UP), description(NEEDS_A_TOUCH_UP), null, AdvancementType.TASK, true, true, false)
                    .addCriterion("obtain_sunken_trident", InventoryChangeTrigger.TriggerInstance.hasItems(AlternaItems.SUNKEN_TRIDENT))
                    .save(saver, Alterna.modId("adventure/needs_a_touch_up"), existingFileHelper);
        }

        private static Component title(String name) {
            return Component.translatable("advancements.alterna." + name + ".title");
        }

        private static Component description(String name) {
            return Component.translatable("advancements.alterna." + name + ".description");
        }
    }
}
