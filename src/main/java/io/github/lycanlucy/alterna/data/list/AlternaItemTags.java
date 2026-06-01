package io.github.lycanlucy.alterna.data.list;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlternaItemTags {
    public static final TagKey<Item> ITEM_RACKS = tag("item_racks");
    public static final TagKey<Item> TRIDENTS = tag("tridents");

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, Alterna.modId(name));
    }
}
