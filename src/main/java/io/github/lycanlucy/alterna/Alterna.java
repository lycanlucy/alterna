package io.github.lycanlucy.alterna;

import com.mojang.logging.LogUtils;
import io.github.lycanlucy.alterna.registry.*;
import net.minecraft.resources.ResourceLocation;
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

        LOGGER.info("Adding deferred registers to the mod bus");
        AlternaItems.register(modEventBus);
        AlternaDataComponents.register(modEventBus);
        AlternaMobEffects.register(modEventBus);
        AlternaEntities.register(modEventBus);
        AlternaParticles.register(modEventBus);
        AlternaSounds.register(modEventBus);
        AlternaInstruments.register(modEventBus);
        AlternaLootModifierSerializers.register(modEventBus);
        AlternaBiomeModifierSerializers.register(modEventBus);
    }

    public static ResourceLocation modId(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }
}
