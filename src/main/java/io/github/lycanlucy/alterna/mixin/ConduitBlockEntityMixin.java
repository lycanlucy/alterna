package io.github.lycanlucy.alterna.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public class ConduitBlockEntityMixin {
    @Inject(method = "applyEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"), cancellable = true)
    private static void cancelIfLordOfTheSkies(Level level, BlockPos pos, List<BlockPos> positions, CallbackInfo ci, @Local Player player) {
        if (player.hasEffect(AlternaMobEffects.LORD_OF_THE_SKIES)) {
            ci.cancel();
        }
    }
}
