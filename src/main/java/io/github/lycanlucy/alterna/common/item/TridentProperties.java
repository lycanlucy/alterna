package io.github.lycanlucy.alterna.common.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TridentProperties(int baseLoyalty, float projectileDamage) {
    public static final TridentProperties DEFAULT = new TridentProperties(1, 8.0F);

    public static final Codec<TridentProperties> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("base_loyalty").forGetter(TridentProperties::baseLoyalty),
                    Codec.FLOAT.fieldOf("projectile_damage").forGetter(TridentProperties::projectileDamage)
            ).apply(instance, TridentProperties::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TridentProperties> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            TridentProperties::baseLoyalty,
            ByteBufCodecs.FLOAT,
            TridentProperties::projectileDamage,
            TridentProperties::new
    );
}