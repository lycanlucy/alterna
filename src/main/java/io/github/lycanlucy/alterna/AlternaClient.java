package io.github.lycanlucy.alterna;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Alterna.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClient {
    public AlternaClient() {
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
    }
}
