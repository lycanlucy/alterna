package io.github.lycanlucy.alterna;

import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Alterna.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClient {
    public AlternaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(AlternaItems.CONCH_SHELL.get(), Alterna.id("blowing"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f);
            ItemProperties.register(AlternaItems.GLIDER.get(), Alterna.id("broken"), (stack, level, entity, seed) -> GliderItem.isUsable(stack) ? 0.0f : 1.0f);
            ItemProperties.register(AlternaItems.GLIDER.get(), Alterna.id("dyed"), (stack, level, entity, seed) -> stack.get(DataComponents.DYED_COLOR) == null ? 0.0f : 1.0f);
        });
    }
}
