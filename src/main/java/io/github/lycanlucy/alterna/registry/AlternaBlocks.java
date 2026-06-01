package io.github.lycanlucy.alterna.registry;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.common.block.ItemRackBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;

public class AlternaBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Alterna.MOD_ID);
    public static final HashMap<DeferredBlock<? extends Block>, String> TRANSLATIONS = new HashMap<>();

    public static final DeferredBlock<Block> OAK_ITEM_RACK = itemRack("oak_item_rack", Blocks.STRIPPED_OAK_LOG.defaultMapColor(), SoundType.WOOD, false);

    private static DeferredBlock<Block> itemRack(String name, MapColor mapColor, SoundType soundType, boolean fireImmune) {
        if (fireImmune) {
            return BLOCKS.register(name, () -> new ItemRackBlock(BlockBehaviour.Properties.of().mapColor(mapColor).sound(soundType).strength(1.0f).noCollission().instrument(NoteBlockInstrument.BASS)));
        } else {
            return BLOCKS.register(name, () -> new ItemRackBlock(BlockBehaviour.Properties.of().mapColor(mapColor).sound(soundType).strength(1.0f).noCollission().instrument(NoteBlockInstrument.BASS).ignitedByLava()));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        TRANSLATIONS.put(OAK_ITEM_RACK, "Oak Item Rack");
    }
}
