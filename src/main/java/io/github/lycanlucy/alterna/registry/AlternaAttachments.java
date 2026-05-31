package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AlternaAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Alterna.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ResourceLocation>> MOB_VARIANT = ATTACHMENTS.register(
            "mob_variant", () -> AttachmentType.builder(MobVariant.OCEAN_SALMON::location).serialize(ResourceLocation.CODEC).sync(ResourceLocation.STREAM_CODEC).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
