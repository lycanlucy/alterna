package io.github.lycanlucy.alterna.common;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.bootstrap.AlternaBiomeModifiers;
import io.github.lycanlucy.alterna.bootstrap.AlternaDamageTypes;
import io.github.lycanlucy.alterna.client.AlternaBuiltinPacks;
import io.github.lycanlucy.alterna.client.AlternaClientColors;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.client.data.AlternaItemModelProvider;
import io.github.lycanlucy.alterna.common.data.*;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import io.github.lycanlucy.alterna.common.entity.Octopus;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.common.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.common.tag.AlternaMobEffectTags;
import io.github.lycanlucy.alterna.common.tag.AlternaMobVariantTags;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            AlternaBuiltinPacks.add(event, AlternaBuiltinPacks.SALMON, "alterna.pack.salmon");
            AlternaBuiltinPacks.add(event, AlternaBuiltinPacks.TRIDENT, "alterna.pack.trident");
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
            AlternaClientConfig.CONFIG.previousRedesignTrident = AlternaClientConfig.redesignTrident();
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
            AlternaClientConfig.CONFIG.previousRedesignTrident = AlternaBuiltinPacks.checkAddAndReload(AlternaBuiltinPacks.TRIDENT.toString(), AlternaClientConfig.redesignTrident());
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.createDatapackRegistryObjects(new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, AlternaDamageTypes::bootstrap).add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, AlternaBiomeModifiers::bootstrap).add(MobVariant.REGISTRY, MobVariant::bootstrap));

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        AlternaBlockTagsProvider blockTags = new AlternaBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new AlternaItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaDamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaInstrumentTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaMobEffectTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new AlternaRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(AlternaBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AlternaEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AlternaChestLoot::new, LootContextParamSets.CHEST)
        ), lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaGLMProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new AlternaDataMapProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new AlternaItemModelProvider(packOutput, existingFileHelper));
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        if (event.getEffectInstance().getEffect().is(AlternaMobEffectTags.UNCLEARABLE)) {
            event.getEffectInstance().getCures().removeIf(EffectCures.DEFAULT_CURES::contains);
        }
    }

    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        AlternaCreativeContents.populateFunctionalBlocks(event);
        AlternaCreativeContents.populateToolsAndUtilities(event);
        AlternaCreativeContents.populateCombat(event);
        AlternaCreativeContents.populateIngredients(event);
        AlternaCreativeContents.populateSpawnEggs(event);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(AlternaEntities.OCTOPUS.get(), Octopus.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(AlternaEntities.OCTOPUS.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(EntityType.DROWNED, (entityType, serverLevel, spawnType, pos, random) -> serverLevel.getBiome(pos).is(Biomes.DRIPSTONE_CAVES) && serverLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(serverLevel, pos, random) && serverLevel.getFluidState(pos).is(Tags.Fluids.WATER) && random.nextInt(20) == 0);
    }

    @SubscribeEvent
    public static void postLivingHurt(LivingDamageEvent.Post event) {
        if (event.getSource().isDirect() && event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getMainHandItem().is(AlternaItemTags.TRIDENTS)) {
                attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.TRIDENT_HIT, attacker.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public static void modifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.TRIDENT, builder -> builder.set(DataComponents.RARITY, Rarity.RARE));
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
        if (entity.level().isClientSide()) {
            if (entity instanceof AbstractFish) {
                float rotation = Mth.lerp(0.05F, entity.getData(AlternaAttachments.SWIM_ROT), (float) entity.getDeltaMovement().y() * 400.0F);
                entity.setData(AlternaAttachments.SWIM_ROT, Mth.clamp(rotation, -70.0F, 70.0F));
            }
        }

        if (entity instanceof LivingEntity) {
            GliderItem.glide((LivingEntity) entity);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (GliderItem.isGliding(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Turtle turtle && turtle.isBaby()) {
            Optional<InteractionResult> interactionResult = Bucketable.bucketMobPickup(event.getEntity(), event.getHand(), (LivingEntity & Bucketable) turtle);
            if (interactionResult.isPresent()) {
                event.setCanceled(true);
                event.setCancellationResult(interactionResult.get());
            }
        }
    }

    @SubscribeEvent
    public static void modifyLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.BLINDNESS)) {
            event.modifyVisibility(0.1);
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
