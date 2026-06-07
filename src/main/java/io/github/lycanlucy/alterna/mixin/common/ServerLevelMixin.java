package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.common.AlternaServerConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow
    @Final
    List<ServerPlayer> players;

    @Inject(method = "wakeUpAllPlayers", at = @At("TAIL"))
    private void resetInsomnia(CallbackInfo ci) {
        if (AlternaServerConfig.insomniaFix()) {
            this.players.forEach(serverPlayer -> serverPlayer.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)));
        }
    }
}