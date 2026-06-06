package io.github.lycanlucy.alterna.common;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.AlternaBuiltinPacks;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaEvents {
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(Alterna.id("packs/salmon"), PackType.CLIENT_RESOURCES, Component.translatable("alterna.pack.salmon"), PackSource.BUILT_IN, false, Pack.Position.TOP);
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == AlternaClientConfig.SPEC) {
            AlternaClientConfig.CONFIG.wasRedesignSalmonEnabled = AlternaClientConfig.redesignSalmon();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == AlternaClientConfig.SPEC) {
            AlternaClientConfig.CONFIG.wasRedesignSalmonEnabled = AlternaBuiltinPacks.checkAddAndReload(AlternaBuiltinPacks.SALMON, AlternaClientConfig.redesignSalmon());
        }
    }
}
