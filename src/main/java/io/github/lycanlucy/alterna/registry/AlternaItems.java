package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.*;
import io.github.lycanlucy.alterna.common.tag.AlternaInstrumentTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alterna.MOD_ID);

    public static final DeferredItem<BlockItem> OAK_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.OAK_ITEM_RACK);
    public static final DeferredItem<BlockItem> SPRUCE_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.SPRUCE_ITEM_RACK);
    public static final DeferredItem<BlockItem> BIRCH_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.BIRCH_ITEM_RACK);
    public static final DeferredItem<BlockItem> JUNGLE_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.JUNGLE_ITEM_RACK);
    public static final DeferredItem<BlockItem> ACACIA_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.ACACIA_ITEM_RACK);
    public static final DeferredItem<BlockItem> DARK_OAK_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.DARK_OAK_ITEM_RACK);
    public static final DeferredItem<BlockItem> MANGROVE_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.MANGROVE_ITEM_RACK);
    public static final DeferredItem<BlockItem> CHERRY_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.CHERRY_ITEM_RACK);
    public static final DeferredItem<BlockItem> BAMBOO_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.BAMBOO_ITEM_RACK);
    public static final DeferredItem<BlockItem> CRIMSON_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.CRIMSON_ITEM_RACK);
    public static final DeferredItem<BlockItem> WARPED_ITEM_RACK = ITEMS.registerSimpleBlockItem(AlternaBlocks.WARPED_ITEM_RACK);
    public static final DeferredItem<Item> SUNKEN_TRIDENT = ITEMS.register("sunken_trident", () -> new TridentItem(new Item.Properties().component(AlternaDataComponents.TRIDENT_PROPERTIES, new TridentProperties(0, 4.0F)).rarity(Rarity.RARE).durability(200).attributes(SunkenTridentItem.createAttributes())));
    public static final DeferredItem<Item> CONCH_SHELL = ITEMS.register("conch_shell", () -> new ConchShellItem(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), AlternaInstrumentTags.CONCH_SHELLS));
    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", GliderItem::new, new Item.Properties().durability(60));
    public static final DeferredItem<Item> BABY_TURTLE_BUCKET = ITEMS.register("baby_turtle_bucket", () -> new MobBucketItem(EntityType.TURTLE, Fluids.WATER, AlternaSounds.BUCKET_EMPTY_BABY_TURTLE.get(), new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY.update(compoundTag -> compoundTag.putInt("Age", -24000)))));
    public static final DeferredItem<Item> VANISH_ITEM_FRAME = ITEMS.registerItem("vanish_item_frame", VanishItemFrameItem::new);
    public static final DeferredItem<Item> INK_BOTTLE = ITEMS.registerItem("ink_bottle", Item::new, new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE));
    public static final DeferredItem<Item> OCTOPUS_SPAWN_EGG = ITEMS.registerItem("octopus_spawn_egg", properties -> new DeferredSpawnEggItem(AlternaEntities.OCTOPUS, 0xf26013, 0xfe9f2a, properties));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ITEMS.addAlias(Alterna.id("trident"), ResourceLocation.withDefaultNamespace("trident"));
    }
}
