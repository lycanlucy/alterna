package io.github.lycanlucy.alterna.client;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.model.OceanSalmonModel;
import io.github.lycanlucy.alterna.client.model.RiverSalmonModel;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClientEvents {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OceanSalmonModel.LAYER_LOCATION, OceanSalmonModel::createBodyLayer);
        event.registerLayerDefinition(RiverSalmonModel.LAYER_LOCATION, RiverSalmonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
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
