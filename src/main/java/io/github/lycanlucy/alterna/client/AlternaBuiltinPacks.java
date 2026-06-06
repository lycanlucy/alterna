package io.github.lycanlucy.alterna.client;

import net.minecraft.client.Minecraft;

public class AlternaBuiltinPacks {
    public static final String SALMON = "mod/alterna:packs/salmon";

    public static boolean checkAddAndReload(String pack, boolean selected) {
        if (pack.equals(SALMON)) {
            if (AlternaClientConfig.CONFIG.wasRedesignSalmonEnabled == selected) return selected;
        }
        if (selected)
            Minecraft.getInstance().getResourcePackRepository().addPack(pack);
        else
            Minecraft.getInstance().getResourcePackRepository().removePack(pack);
        Minecraft.getInstance().delayTextureReload();
        return selected;
    }
}
