package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.AlternaThrownTridentRenderer;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClientEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AlternaEntities.TRIDENT.get(), AlternaThrownTridentRenderer::new);
    }
}
