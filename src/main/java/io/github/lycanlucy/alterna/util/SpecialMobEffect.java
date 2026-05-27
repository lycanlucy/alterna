package io.github.lycanlucy.alterna.util;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaMobEffects;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;

public interface SpecialMobEffect {
    ResourceLocation GUI_BACKGROUND_SPRITE = Alterna.modId("hud/effect_background_special");
    ResourceLocation INVENTORY_BACKGROUND_SPRITE_SMALL = Alterna.modId("container/inventory/effect_background_special_small");
    ResourceLocation INVENTORY_BACKGROUND_SPRITE_LARGE = Alterna.modId("container/inventory/effect_background_special_large");

    // List of mob effects that cannot be removed normally (ie with milk)
    ArrayList<Holder<MobEffect>> SPECIAL_MOB_EFFECTS = Util.make(Lists.newArrayList(), holders -> {
        holders.add(MobEffects.CONDUIT_POWER);
        holders.add(AlternaMobEffects.LORD_OF_THE_SKIES);
    });
}
