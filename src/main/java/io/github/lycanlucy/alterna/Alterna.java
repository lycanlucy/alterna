package io.github.lycanlucy.alterna;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Alterna.MOD_ID)
public class Alterna {
    public static final String MOD_ID = "alterna";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Alterna(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }
}
