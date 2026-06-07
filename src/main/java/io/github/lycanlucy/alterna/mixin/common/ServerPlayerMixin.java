package io.github.lycanlucy.alterna.mixin.common;

import com.mojang.authlib.GameProfile;
import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "startSleeping", at = @At("HEAD"), cancellable = true)
    private void doNotResetInsomnia(BlockPos pos, CallbackInfo ci) {
        if (AlternaServerConfig.insomniaFix()) {
            super.startSleeping(pos);
            ci.cancel();
        }
    }
}