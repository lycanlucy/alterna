package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import io.github.lycanlucy.alterna.data.list.AlternaItemTags;
import io.github.lycanlucy.alterna.data.list.AlternaMobVariantTags;
import io.github.lycanlucy.alterna.data.list.AlternaRegistries;
import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import io.github.lycanlucy.alterna.util.CreativeModeTabHelper;
import io.github.lycanlucy.alterna.util.SpecialMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.Optional;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(AlternaRegistries.MOB_VARIANT, MobVariant.CODEC, MobVariant.CODEC);
    }

    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTabHelper.populateToolsAndUtilities(event);
        CreativeModeTabHelper.populateCombat(event);
    }

    @SubscribeEvent
    public static void onMobEffectAdded(MobEffectEvent.Added event) {
        if (SpecialMobEffect.SPECIAL_MOB_EFFECTS.contains(event.getEffectInstance().getEffect())) {
            event.getEffectInstance().getCures().removeIf(EffectCures.DEFAULT_CURES::contains);
        }
    }

    @SubscribeEvent
    public static void onPostLivingHurt(LivingDamageEvent.Post event) {
        if (event.getSource().isDirect() && event.getSource().getEntity() instanceof LivingEntity attacker) {
            if (attacker.getMainHandItem().is(AlternaItemTags.TRIDENTS)) {
                attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.TRIDENT_HIT, attacker.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(EntityType.DROWNED, (entityType, serverLevel, spawnType, pos, random) -> serverLevel.getBiome(pos).is(Biomes.DRIPSTONE_CAVES) && serverLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(serverLevel, pos, random) && serverLevel.getFluidState(pos).is(Tags.Fluids.WATER) && random.nextInt(20) == 0);
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob entity = event.getEntity();
        if (entity.getType() == EntityType.SALMON) {
            Optional<Holder.Reference<MobVariant>> key = MobVariant.getSpawnVariant(entity, AlternaMobVariantTags.SALMON, entity.level().getBiome(entity.blockPosition()));
            entity.setData(AlternaAttachments.MOB_VARIANT, key.map(holder -> holder.getKey().location()).orElseGet(MobVariant.OCEAN_SALMON::location));
        }
    }

    @SuppressWarnings("ConstantConditions")
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
