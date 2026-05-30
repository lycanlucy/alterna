package io.github.lycanlucy.alterna.data.server.loot;

import io.github.lycanlucy.alterna.data.list.AlternaLootTables;
import io.github.lycanlucy.alterna.data.list.AlternaInstrumentTags;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public record AlternaChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(AlternaLootTables.BURIED_TREASURE_ADDITIONS, LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(AlternaItems.CONCH_SHELL))
                                .apply(SetInstrumentFunction.setInstrumentOptions(AlternaInstrumentTags.CONCH_SHELLS))
                )
        );
    }
}
