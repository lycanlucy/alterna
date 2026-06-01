package io.github.lycanlucy.alterna.data.list;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AlternaBlockTags {
    public static final TagKey<Block> ITEM_RACKS = tag("item_racks");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registries.BLOCK, Alterna.modId(name));
    }
}
