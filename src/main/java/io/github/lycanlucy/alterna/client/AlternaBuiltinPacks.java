package io.github.lycanlucy.alterna.client;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;
import java.util.Optional;

public class AlternaBuiltinPacks {
    public static final ResourceLocation SALMON = Alterna.id("packs/salmon");

    public static void add(AddPackFindersEvent event, ResourceLocation name) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;
        Path path = ModList.get().getModFileById(Alterna.MOD_ID).getFile().findResource(name.getPath());

        Pack pack = Pack.readMetaAndCreate(new PackLocationInfo(name.toString(), Component.translatable("alterna.pack.salmon"), PackSource.BUILT_IN, Optional.empty()), BuiltInPackSource.fromName(packLocationInfo -> new PathPackResources(packLocationInfo, path)), PackType.CLIENT_RESOURCES, new PackSelectionConfig(false, Pack.Position.TOP, false));

        event.addRepositorySource(onLoad -> onLoad.accept(pack));
    }

    public static boolean checkAddAndReload(String pack, boolean selected) {
        if (pack.equals(SALMON.toString())) {
            if (AlternaClientConfig.CONFIG.previousRedesignSalmon == selected) return selected;
        }
        if (selected) {
            Minecraft.getInstance().getResourcePackRepository().addPack(pack);
            Minecraft.getInstance().delayTextureReload();
        } else {
            Minecraft.getInstance().getResourcePackRepository().removePack(pack);
            Minecraft.getInstance().delayTextureReload();
        }
        return selected;
    }
}
