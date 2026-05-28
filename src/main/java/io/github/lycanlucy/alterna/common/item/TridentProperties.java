package io.github.lycanlucy.alterna.common.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.Ingredient;

public record TridentProperties(boolean hasLoyaltyByDefault, int enchantability, Ingredient repairIngredient,
                                float projectileDamage) {
    public static final TridentProperties DEFAULT = new TridentProperties(false, 1, Ingredient.EMPTY, 8.0f);

    public static final Codec<TridentProperties> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("has_loyalty_by_default").forGetter(TridentProperties::hasLoyaltyByDefault),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("enchantability").forGetter(TridentProperties::enchantability),
                    Ingredient.CODEC.fieldOf("repair_ingredient").forGetter(TridentProperties::repairIngredient),
                    Codec.FLOAT.fieldOf("projectile_damage").forGetter(TridentProperties::projectileDamage)
            ).apply(instance, TridentProperties::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TridentProperties> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            TridentProperties::hasLoyaltyByDefault,
            ByteBufCodecs.VAR_INT,
            TridentProperties::enchantability,
            Ingredient.CONTENTS_STREAM_CODEC,
            TridentProperties::repairIngredient,
            ByteBufCodecs.FLOAT,
            TridentProperties::projectileDamage,
            TridentProperties::new
    );
}
