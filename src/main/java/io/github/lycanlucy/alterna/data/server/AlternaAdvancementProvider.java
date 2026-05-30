package io.github.lycanlucy.alterna.data.server;

import com.mojang.datafixers.util.Pair;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        @Override
        public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
            for (AlternaAdvancements.AlternaAdvancement advancement : AlternaAdvancements.ADVANCEMENTS) {
                Advancement.Builder builder = Advancement.Builder.advancement();
                ResourceLocation background = null;
                if (advancement.background().isPresent()) {
                    background = advancement.background().get();
                }

                advancement.parent().ifPresent(resourceLocation -> builder.parent(AdvancementSubProvider.createPlaceholder(resourceLocation.toString())));
                builder.display(advancement.icon(), Component.translatable(AlternaAdvancements.title(advancement.name())), Component.translatable(AlternaAdvancements.description(advancement.name())), background, advancement.type(), background == null, background == null, false);

                for (Pair<String, Criterion<?>> pair : advancement.criteria()) {
                    builder.addCriterion(pair.getFirst(), pair.getSecond());
                }

                builder.save(saver, Alterna.modId(advancement.name().replace('.', '/')), existingFileHelper);
            }
        }
    }
}
