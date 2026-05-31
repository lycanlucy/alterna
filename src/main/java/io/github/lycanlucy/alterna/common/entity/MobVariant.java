package io.github.lycanlucy.alterna.common.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.event.AlternaRegistries;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record MobVariant(ResourceLocation texture, String model) {
    public static final Codec<MobVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.fieldOf("texture").forGetter(MobVariant::texture), Codec.STRING.fieldOf("model").forGetter(MobVariant::model)).apply(instance, MobVariant::new));
    public static final ResourceKey<MobVariant> OCEAN_SALMON = key("ocean_salmon");
    public static final ResourceKey<MobVariant> RIVER_SALMON = key("river_salmon");

    public static void bootstrap(BootstrapContext<MobVariant> context) {
        context.register(OCEAN_SALMON, new MobVariant(Alterna.modId("textures/entity/fish/ocean_salmon.png"), "ocean"));
        context.register(RIVER_SALMON, new MobVariant(Alterna.modId("textures/entity/fish/river_salmon.png"), "river"));
    }

    public static MobVariant getFor(Entity entity) {
        return getRegistry(entity).get(entity.getData(AlternaAttachments.MOB_VARIANT));
    }

    public static Registry<MobVariant> getRegistry(Entity entity) {
        return entity.registryAccess().registryOrThrow(AlternaRegistries.MOB_VARIANT);
    }

    private static ResourceKey<MobVariant> key(String name) {
        return ResourceKey.create(AlternaRegistries.MOB_VARIANT, Alterna.modId(name));
    }
}
