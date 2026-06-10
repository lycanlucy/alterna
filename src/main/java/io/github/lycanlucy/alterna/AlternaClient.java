package io.github.lycanlucy.alterna;

import io.github.lycanlucy.alterna.client.AlternaBuiltinPacks;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Alterna.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Alterna.MOD_ID, value = Dist.CLIENT)
public class AlternaClient {
    private static boolean reloadPacks = false;

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

        if (AlternaClientConfig.redesignSalmon()) {
            reloadPacks = Minecraft.getInstance().getResourcePackRepository().addPack(AlternaBuiltinPacks.SALMON.toString());
        } else {
            reloadPacks = Minecraft.getInstance().getResourcePackRepository().removePack(AlternaBuiltinPacks.SALMON.toString());
        }
        if (AlternaClientConfig.redesignTrident()) {
            reloadPacks = Minecraft.getInstance().getResourcePackRepository().addPack(AlternaBuiltinPacks.TRIDENT.toString());
        } else {
            reloadPacks = Minecraft.getInstance().getResourcePackRepository().removePack(AlternaBuiltinPacks.TRIDENT.toString());
        }
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
        if (reloadPacks)
            Minecraft.getInstance().reloadResourcePacks();
    }
}
