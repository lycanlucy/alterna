package io.github.lycanlucy.alterna.registry;

import com.mojang.serialization.Codec;
import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.entity.MobVariant;
import net.minecraft.network.codec.ByteBufCodecs;
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
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> SWIM_ROT = ATTACHMENTS.register(
            "swim_rot", () -> AttachmentType.builder(() -> 0.0f).serialize(Codec.FLOAT).sync(ByteBufCodecs.FLOAT).build()
    );
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> GLIDER_GLIDING_TICKS = ATTACHMENTS.register(
            "glider_gliding_ticks", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).sync(ByteBufCodecs.INT).build()
    );
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> GLIDER_MOMENTUM = ATTACHMENTS.register(
            "glider_momentum", () -> AttachmentType.builder(() -> 1.0F).serialize(Codec.FLOAT).sync(ByteBufCodecs.FLOAT).build()
    );
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> GLIDER_BOOSTING = ATTACHMENTS.register(
            "glider_boosting", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).sync(ByteBufCodecs.BOOL).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
