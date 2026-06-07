package io.github.lycanlucy.alterna.client;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class AlternaReloadListener extends SimplePreparableReloadListener<boolean[]> {
    @Override
    protected boolean[] prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return new boolean[]{AlternaClientConfig.redesignSalmon(), AlternaClientConfig.redesignTrident()};
    }

    @Override
    protected void apply(boolean[] object, ResourceManager resourceManager, ProfilerFiller profiler) {
        if (object[0]) {
            Minecraft.getInstance().getResourcePackRepository().addPack(AlternaBuiltinPacks.SALMON.toString());
        } else {
            Minecraft.getInstance().getResourcePackRepository().removePack(AlternaBuiltinPacks.SALMON.toString());
        }
        if (object[1]) {
            Minecraft.getInstance().getResourcePackRepository().addPack(AlternaBuiltinPacks.TRIDENT.toString());
        } else {
            Minecraft.getInstance().getResourcePackRepository().removePack(AlternaBuiltinPacks.TRIDENT.toString());
        }
    }
}
