package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.loot.RemoveItemLootModifier;
import io.github.lycanlucy.alterna.common.loot.ReplaceItemLootModifier;
import io.github.lycanlucy.alterna.data.list.AlternaInstrumentTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.data.list.AlternaLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AlternaLootModifierProvider extends GlobalLootModifierProvider {
    public AlternaLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Alterna.MOD_ID);
    }

    @Override
    protected void start() {
        add("add_to_buried_treasure", new AddTableLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(BuiltInLootTables.BURIED_TREASURE.location()).build()
        }, AlternaLootTables.BURIED_TREASURE_ADDITIONS));

        add("add_conch_shell_to_fishing_treasure", new ReplaceItemLootModifier(new LootItemCondition[]{
                LootItemRandomChanceCondition.randomChance(0.5f).build(),
                LootTableIdCondition.builder(BuiltInLootTables.FISHING.location()).build()
        }, Items.NAUTILUS_SHELL, AlternaItems.CONCH_SHELL.get(), List.of(SetInstrumentFunction.setInstrumentOptions(AlternaInstrumentTags.CONCH_SHELLS).build())));

        add("remove_trident_from_trial_chambers", new RemoveItemLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(BuiltInLootTables.TRIAL_CHAMBERS_REWARD.location()).build()
        }, Items.TRIDENT));
    }
}
