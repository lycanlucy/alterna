package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.server.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.util.CreativeModeTabHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTabHelper.populateCombat(event);
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
}
