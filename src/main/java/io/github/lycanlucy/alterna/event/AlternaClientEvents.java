package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.model.OceanSalmonModel;
import io.github.lycanlucy.alterna.client.model.RiverSalmonModel;
import io.github.lycanlucy.alterna.client.particle.SlowSpellParticle;
import io.github.lycanlucy.alterna.client.renderer.AlternaThrownTridentRenderer;
import io.github.lycanlucy.alterna.registry.AlternaEntities;
import io.github.lycanlucy.alterna.registry.AlternaParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClientEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(AlternaEntities.TRIDENT.get(), AlternaThrownTridentRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OceanSalmonModel.LAYER_LOCATION, OceanSalmonModel::createBodyLayer);
        event.registerLayerDefinition(RiverSalmonModel.LAYER_LOCATION, RiverSalmonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AlternaParticles.LORD_OF_THE_SKIES.get(), SlowSpellParticle.Provider::new);
    }
}
