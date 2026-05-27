package io.github.lycanlucy.alterna.util;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class AlternaLootTables {
    public static final ResourceKey<LootTable> BURIED_TREASURE_ADDITIONS = key("chests/buried_treasure_additions");

    private static ResourceKey<LootTable> key(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Alterna.modId(name));
    }
}
