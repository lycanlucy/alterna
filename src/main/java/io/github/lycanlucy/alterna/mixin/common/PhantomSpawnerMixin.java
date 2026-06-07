package io.github.lycanlucy.alterna.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.registry.AlternaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/event/entity/player/PlayerSpawnPhantomsEvent;getPhantomsToSpawn()I"))
    private void triggerCriteria(ServerLevel serverLevel, boolean spawnEnemies, boolean spawnFriendlies, CallbackInfoReturnable<Integer> cir, @Local ServerPlayer serverPlayer) {
        AlternaTriggers.SPAWNED_PHANTOMS.get().trigger(serverPlayer);
    }
}