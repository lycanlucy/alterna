package io.github.lycanlucy.alterna.event;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = Alterna.MOD_ID)
public class AlternaRegistries {
    public static final ResourceKey<Registry<MobVariant>> MOB_VARIANT = ResourceKey.createRegistryKey(Alterna.modId("mob_variant"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MOB_VARIANT, MobVariant.CODEC, MobVariant.CODEC);
    }
}
