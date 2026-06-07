package io.github.lycanlucy.alterna.common.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlternaItemTags {
    public static final TagKey<Item> ITEM_RACKS = tag("item_racks");
    public static final TagKey<Item> TRIDENTS = tag("tridents");
    public static final TagKey<Item> TRIDENT_REPAIR_MATERIALS = tag("trident_repair_materials");

    public static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, Alterna.id(name));
    }
}