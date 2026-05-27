package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.util.CreativeModeTabHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTabHelper.populateCombat(event);
    }
}
