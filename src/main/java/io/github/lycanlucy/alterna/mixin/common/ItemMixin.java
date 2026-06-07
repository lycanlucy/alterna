package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.bootstrap.AlternaDamageTypes;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void addUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        boolean isInkSac = itemStack.is(Items.INK_SAC);

        if (isInkSac || itemStack.is(Items.GLOW_INK_SAC)) {
            if (level instanceof ServerLevel) {
                for (Entity entity : level.getEntities(player, player.getBoundingBox().inflate(2.4), EntitySelector.LIVING_ENTITY_STILL_ALIVE)) {
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(isInkSac ? MobEffects.BLINDNESS : MobEffects.GLOWING, 240), player);
                    entity.hurt(AlternaDamageTypes.inkSac(player), 0.0f);
                    if (entity instanceof Mob) {
                        ((Mob) entity).setTarget(null);
                    }
                }
                for (int i = 0; i < 30; i++) {
                    ((ServerLevel) level).sendParticles(isInkSac ? ParticleTypes.SQUID_INK : ParticleTypes.GLOW_SQUID_INK, player.getRandomX(2.4), player.getRandomY(), player.getRandomZ(2.4), 0, 0.0, 0.0, 0.0, 0.0);
                }
            }
            player.playSound(isInkSac ? AlternaSounds.INK_SAC_SPRAY.get() : AlternaSounds.GLOW_INK_SAC_SPRAY.get(), 1.0F, 1.0F);
            Item item = (Item) (Object) this;
            player.getCooldowns().addCooldown(item, 20);
            player.awardStat(Stats.ITEM_USED.get(item));
            itemStack.consume(1, player);
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide));
        }
    }
}