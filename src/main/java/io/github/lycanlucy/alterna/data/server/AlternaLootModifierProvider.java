package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.loot.RemoveItemLootModifier;
import io.github.lycanlucy.alterna.util.AlternaLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

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
        add("remove_trident_from_trial_chambers", new RemoveItemLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(BuiltInLootTables.TRIAL_CHAMBERS_REWARD.location()).build()
        }, Items.TRIDENT));
    }
}
