package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.AlternaTridentItem;
import io.github.lycanlucy.alterna.data.server.tag.AlternaInstrumentTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alterna.MOD_ID);

    public static final DeferredItem<Item> TRIDENT = ITEMS.register("trident", () -> new AlternaTridentItem(new Item.Properties().rarity(Rarity.RARE).durability(250).attributes(AlternaTridentItem.createAttributes(AlternaTridentItem.TRIDENT_BASE_DAMAGE)).component(AlternaDataComponents.PROJECTILE_BASE_DAMAGE, AlternaTridentItem.TRIDENT_BASE_DAMAGE).component(DataComponents.TOOL, AlternaTridentItem.createToolProperties())));
    public static final DeferredItem<Item> SUNKEN_TRIDENT = ITEMS.register("sunken_trident", () -> new AlternaTridentItem(new Item.Properties().rarity(Rarity.RARE).durability(200).attributes(AlternaTridentItem.createAttributes(AlternaTridentItem.SUNKEN_TRIDENT_BASE_DAMAGE)).component(AlternaDataComponents.PROJECTILE_BASE_DAMAGE, AlternaTridentItem.SUNKEN_TRIDENT_BASE_DAMAGE).component(DataComponents.TOOL, AlternaTridentItem.createToolProperties())));
    public static final DeferredItem<Item> CONCH_SHELL = ITEMS.register("conch_shell", () -> new InstrumentItem(new Item.Properties().stacksTo(1), AlternaInstrumentTags.CONCH_SHELLS));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
