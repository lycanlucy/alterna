package io.github.lycanlucy.alterna.common.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.list.AlternaRegistries;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public record MobVariant(ResourceLocation texture, String model, HolderSet<Biome> biomes) {
    public static final Codec<MobVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(ResourceLocation.CODEC.fieldOf("texture").forGetter(MobVariant::texture), Codec.STRING.fieldOf("model").forGetter(MobVariant::model), RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(MobVariant::biomes)).apply(instance, MobVariant::new));
    public static final ResourceKey<MobVariant> OCEAN_SALMON = key("ocean_salmon");
    public static final ResourceKey<MobVariant> RIVER_SALMON = key("river_salmon");
    public static final String OCEAN_SALMON_MODEL = "ocean";
    public static final String RIVER_SALMON_MODEL = "river";

    public static void bootstrap(BootstrapContext<MobVariant> context) {
        context.register(OCEAN_SALMON, new MobVariant(Alterna.modId("textures/entity/fish/ocean_salmon.png"), OCEAN_SALMON_MODEL, context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OCEAN)));
        context.register(RIVER_SALMON, new MobVariant(Alterna.modId("textures/entity/fish/river_salmon.png"), RIVER_SALMON_MODEL, context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_RIVER)));
    }

    public static Optional<Holder.Reference<MobVariant>> getSpawnVariant(Entity entity, TagKey<MobVariant> tag, Holder<Biome> biome) {
        Registry<MobVariant> registry = getRegistry(entity);
        return registry.holders()
                .filter(holder -> holder.is(tag) && holder.value().biomes().contains(biome))
                .findFirst();
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
