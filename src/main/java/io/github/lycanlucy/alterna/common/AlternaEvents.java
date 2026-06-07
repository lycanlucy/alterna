package io.github.lycanlucy.alterna.common;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.AlternaBuiltinPacks;
import io.github.lycanlucy.alterna.client.AlternaClientColors;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import io.github.lycanlucy.alterna.common.tag.AlternaMobVariantTags;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.AbstractFish;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Optional;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            AlternaBuiltinPacks.add(event, AlternaBuiltinPacks.SALMON);
        }
    }

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MobVariant.REGISTRY, MobVariant.CODEC, MobVariant.CODEC);
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == AlternaClientConfig.SPEC) {
            AlternaClientConfig.CONFIG.previousModifyBiomeColors = AlternaClientConfig.modifyBiomeColors();
            AlternaClientConfig.CONFIG.previousRedesignSalmon = AlternaClientConfig.redesignSalmon();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == AlternaClientConfig.SPEC) {
            if (AlternaClientConfig.CONFIG.previousModifyBiomeColors != AlternaClientConfig.modifyBiomeColors()) {
                AlternaClientConfig.CONFIG.previousModifyBiomeColors = AlternaClientConfig.modifyBiomeColors();
                Minecraft.getInstance().delayTextureReload();
            }
            if (AlternaClientConfig.CONFIG.previousAquariumOpacity != AlternaClientConfig.aquariumOpacity()) {
                AlternaClientConfig.CONFIG.previousAquariumOpacity = AlternaClientConfig.aquariumOpacity();
                Minecraft.getInstance().delayTextureReload();
            }
            AlternaClientConfig.CONFIG.previousRedesignSalmon = AlternaBuiltinPacks.checkAddAndReload(AlternaBuiltinPacks.SALMON.toString(), AlternaClientConfig.redesignSalmon());
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.createDatapackRegistryObjects(new RegistrySetBuilder().add(MobVariant.REGISTRY, MobVariant::bootstrap));
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel().isClientSide()) {
            AlternaClientColors.initializeBiomeColors(event.getLevel());
        }
    }

    @SubscribeEvent
    public static void postEntityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity.level().isClientSide() && entity instanceof AbstractFish) {
            float rotation = Mth.lerp(0.05F, entity.getData(AlternaAttachments.SWIM_ROT), (float) entity.getDeltaMovement().y() * 400.0F);
            entity.setData(AlternaAttachments.SWIM_ROT, Mth.clamp(rotation, -70.0F, 70.0F));
        }
    }

    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob entity = event.getEntity();
        if (entity.getType() == EntityType.SALMON) {
            Optional<Holder.Reference<MobVariant>> key = MobVariant.getSpawnVariant(entity, AlternaMobVariantTags.SALMON, entity.level().getBiome(entity.blockPosition()));
            entity.setData(AlternaAttachments.MOB_VARIANT, key.map(holder -> holder.getKey().location()).orElseGet(MobVariant.OCEAN_SALMON::location));
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) return;
        Entity entity = event.getEntity();

        if (!entity.hasData(AlternaAttachments.MOB_VARIANT)) {
            if (entity.getType() == EntityType.SALMON) {
                Optional<Holder.Reference<MobVariant>> key = MobVariant.getSpawnVariant(entity, AlternaMobVariantTags.SALMON, entity.level().getBiome(entity.blockPosition()));
                entity.setData(AlternaAttachments.MOB_VARIANT, key.map(holder -> holder.getKey().location()).orElseGet(MobVariant.OCEAN_SALMON::location));
            }
        }
    }
}
