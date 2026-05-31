package io.github.lycanlucy.alterna.data.list;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class AlternaRegistries {
    public static final ResourceKey<Registry<MobVariant>> MOB_VARIANT = ResourceKey.createRegistryKey(Alterna.modId("mob_variant"));
}
