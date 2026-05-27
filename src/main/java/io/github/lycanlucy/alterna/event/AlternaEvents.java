package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.data.server.tag.AlternaItemTags;
import io.github.lycanlucy.alterna.util.CreativeModeTabHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
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
}
