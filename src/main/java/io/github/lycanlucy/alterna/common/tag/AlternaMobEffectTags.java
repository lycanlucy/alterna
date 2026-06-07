package io.github.lycanlucy.alterna.common.tag;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class AlternaMobEffectTags {
    public static final TagKey<MobEffect> UNCLEARABLE = tag("unclearable");

    private static TagKey<MobEffect> tag(String name) {
        return TagKey.create(Registries.MOB_EFFECT, Alterna.id(name));
    }
}
