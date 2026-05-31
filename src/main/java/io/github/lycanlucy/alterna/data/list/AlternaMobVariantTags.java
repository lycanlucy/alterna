package io.github.lycanlucy.alterna.data.list;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.tags.TagKey;

public class AlternaMobVariantTags {
    public static final TagKey<MobVariant> SALMON = tag("salmon");

    private static TagKey<MobVariant> tag(String name) {
        return TagKey.create(AlternaRegistries.MOB_VARIANT, Alterna.modId(name));
    }
}
