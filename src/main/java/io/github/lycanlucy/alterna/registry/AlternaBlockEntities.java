package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.block.ItemRackBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("ConstantConditions")
public class AlternaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Alterna.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemRackBlockEntity>> ITEM_RACK = BLOCK_ENTITIES.register(
            "item_rack",
            () -> BlockEntityType.Builder.of(ItemRackBlockEntity::new,
                    AlternaBlocks.OAK_ITEM_RACK.get(),
                    AlternaBlocks.SPRUCE_ITEM_RACK.get(),
                    AlternaBlocks.BIRCH_ITEM_RACK.get(),
                    AlternaBlocks.JUNGLE_ITEM_RACK.get(),
                    AlternaBlocks.ACACIA_ITEM_RACK.get(),
                    AlternaBlocks.DARK_OAK_ITEM_RACK.get(),
                    AlternaBlocks.MANGROVE_ITEM_RACK.get(),
                    AlternaBlocks.CHERRY_ITEM_RACK.get(),
                    AlternaBlocks.BAMBOO_ITEM_RACK.get(),
                    AlternaBlocks.CRIMSON_ITEM_RACK.get(),
                    AlternaBlocks.WARPED_ITEM_RACK.get()
            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
