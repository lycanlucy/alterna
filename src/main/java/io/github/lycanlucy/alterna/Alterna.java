package io.github.lycanlucy.alterna;

import com.mojang.logging.LogUtils;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Alterna.MOD_ID)
public class Alterna {
    public static final String MOD_ID = "alterna";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Alterna(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::commonSetup);

        container.registerConfig(ModConfig.Type.CLIENT, AlternaClientConfig.SPEC);
    }

    /**
     *
     * @param path the object's ID
     * @return a new {@link net.minecraft.resources.ResourceLocation} with the mod's namespace and the given path
     */
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
}
