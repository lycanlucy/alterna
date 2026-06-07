package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alterna.MOD_ID);

    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", GliderItem::new, new Item.Properties().durability(60));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ITEMS.addAlias(Alterna.id("trident"), ResourceLocation.withDefaultNamespace("trident"));
    }
}
