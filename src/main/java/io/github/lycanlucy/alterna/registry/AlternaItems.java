package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.item.GliderItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlternaItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alterna.MOD_ID);

    public static final DeferredItem<Item> GLIDER = ITEMS.registerItem("glider", GliderItem::new, new Item.Properties().durability(60));
    public static final DeferredItem<Item> BABY_TURTLE_BUCKET = ITEMS.register("baby_turtle_bucket", () -> new MobBucketItem(EntityType.TURTLE, Fluids.WATER, AlternaSounds.BUCKET_EMPTY_BABY_TURTLE.get(), new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY.update(compoundTag -> compoundTag.putInt("Age", -24000)))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ITEMS.addAlias(Alterna.id("trident"), ResourceLocation.withDefaultNamespace("trident"));
    }
}
