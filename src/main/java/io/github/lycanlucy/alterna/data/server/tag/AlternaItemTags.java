package io.github.lycanlucy.alterna.data.server.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlternaItemTags {
    public static final TagKey<Item> TRIDENTS = tag("tridents");

    public static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, Alterna.modId(name));
    }
}
