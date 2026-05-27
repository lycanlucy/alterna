package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TridentItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alterna.MOD_ID);

    public static final DeferredItem<Item> TRIDENT = ITEMS.registerItem("trident", TridentItem::new, new Item.Properties().rarity(Rarity.RARE).durability(250).attributes(TridentItem.createAttributes()).component(DataComponents.TOOL, TridentItem.createToolProperties()));
    public static final DeferredItem<Item> SUNKEN_TRIDENT = ITEMS.registerItem("sunken_trident", TridentItem::new, new Item.Properties().rarity(Rarity.RARE).durability(200).attributes(TridentItem.createAttributes()).component(DataComponents.TOOL, TridentItem.createToolProperties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
