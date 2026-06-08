package io.github.lycanlucy.alterna.client;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.layer.GliderLayer;
import io.github.lycanlucy.alterna.client.model.GliderModel;
import io.github.lycanlucy.alterna.client.model.OceanSalmonModel;
import io.github.lycanlucy.alterna.client.model.RiverSalmonModel;
import io.github.lycanlucy.alterna.client.particle.AuraParticle;
import io.github.lycanlucy.alterna.client.renderer.ItemRackRenderer;
import io.github.lycanlucy.alterna.common.EnumParams;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.registry.AlternaBlockEntities;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaParticles;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClientEvents {
    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new AlternaReloadListener());
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OceanSalmonModel.LAYER_LOCATION, OceanSalmonModel::createBodyLayer);
        event.registerLayerDefinition(RiverSalmonModel.LAYER_LOCATION, RiverSalmonModel::createBodyLayer);
        event.registerLayerDefinition(GliderModel.LAYER_LOCATION, GliderModel::createLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(AlternaBlockEntities.ITEM_RACK.get(), ItemRackRenderer::new);
    }

    @SubscribeEvent
    public static void addEntityRendererLayers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            PlayerRenderer skin = event.getSkin(model);
            if (skin != null) {
                skin.addLayer(new GliderLayer<>(skin, event.getEntityModels()));
            }
        });
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(AlternaParticles.LORD_OF_THE_SKIES.get(), AuraParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return GliderItem.isGliding(entityLiving) ? EnumParams.GLIDER.getValue() : null;
            }
        }, AlternaItems.GLIDER);
    }


    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> stack.get(DataComponents.DYED_COLOR) == null || tintIndex > 0 ? -1 : IClientItemExtensions.of(stack).getDefaultDyeColor(stack), AlternaItems.GLIDER.get());
        event.register((stack, tintIndex) -> {
            if (AlternaClientConfig.redesignSalmon()) {
                return FastColor.ARGB32.opaque(AlternaClientColors.salmonSpawnEgg(tintIndex));
            } else {
                return FastColor.ARGB32.opaque(((SpawnEggItem) stack.getItem()).getColor(tintIndex));
            }
        }, Items.SALMON_SPAWN_EGG);
    }

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(AlternaClientColors.WATER_COLOR_RESOLVER);
    }
}
