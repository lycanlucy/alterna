package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.block.ItemRackBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;

public class AlternaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Alterna.MOD_ID);
    public static final HashMap<DeferredBlock<? extends Block>, String> TRANSLATIONS = new HashMap<>();

    public static final DeferredBlock<Block> OAK_ITEM_RACK = itemRack("oak_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> SPRUCE_ITEM_RACK = itemRack("spruce_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> BIRCH_ITEM_RACK = itemRack("birch_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> JUNGLE_ITEM_RACK = itemRack("jungle_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> ACACIA_ITEM_RACK = itemRack("acacia_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> DARK_OAK_ITEM_RACK = itemRack("dark_oak_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> MANGROVE_ITEM_RACK = itemRack("mangrove_item_rack", SoundType.WOOD, false);
    public static final DeferredBlock<Block> CHERRY_ITEM_RACK = itemRack("cherry_item_rack", SoundType.CHERRY_WOOD, false);
    public static final DeferredBlock<Block> BAMBOO_ITEM_RACK = itemRack("bamboo_item_rack", SoundType.BAMBOO_WOOD, false);
    public static final DeferredBlock<Block> CRIMSON_ITEM_RACK = itemRack("crimson_item_rack", SoundType.NETHER_WOOD, true);
    public static final DeferredBlock<Block> WARPED_ITEM_RACK = itemRack("warped_item_rack", SoundType.NETHER_WOOD, true);

    private static DeferredBlock<Block> itemRack(String name, SoundType soundType, boolean fireImmune) {
        if (fireImmune) {
            return BLOCKS.register(name, () -> new ItemRackBlock(BlockBehaviour.Properties.of().sound(soundType).strength(1.0f).noCollission().instrument(NoteBlockInstrument.BASS)));
        } else {
            return BLOCKS.register(name, () -> new ItemRackBlock(BlockBehaviour.Properties.of().sound(soundType).strength(1.0f).noCollission().instrument(NoteBlockInstrument.BASS).ignitedByLava()));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TRANSLATIONS.put(OAK_ITEM_RACK, "Oak Item Rack");
        TRANSLATIONS.put(SPRUCE_ITEM_RACK, "Spruce Item Rack");
        TRANSLATIONS.put(BIRCH_ITEM_RACK, "Birch Item Rack");
        TRANSLATIONS.put(JUNGLE_ITEM_RACK, "Jungle Item Rack");
        TRANSLATIONS.put(ACACIA_ITEM_RACK, "Acacia Item Rack");
        TRANSLATIONS.put(DARK_OAK_ITEM_RACK, "Dark Oak Item Rack");
        TRANSLATIONS.put(MANGROVE_ITEM_RACK, "Mangrove Item Rack");
        TRANSLATIONS.put(CHERRY_ITEM_RACK, "Cherry Item Rack");
        TRANSLATIONS.put(BAMBOO_ITEM_RACK, "Bamboo Item Rack");
        TRANSLATIONS.put(CRIMSON_ITEM_RACK, "Crimson Item Rack");
        TRANSLATIONS.put(WARPED_ITEM_RACK, "Warped Item Rack");
    }
}
