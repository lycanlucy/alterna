package io.github.lycanlucy.alterna.common.entity;

import io.github.lycanlucy.alterna.registry.AlternaEntities;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VanishItemFrame extends ItemFrame {
    public VanishItemFrame(EntityType<? extends ItemFrame> entityType, Level level) {
        super(entityType, level);
    }

    public VanishItemFrame(Level level, BlockPos blockPos, Direction direction) {
        super(AlternaEntities.VANISH_ITEM_FRAME.get(), level, blockPos, direction);
    }

    @Override
    public SoundEvent getRemoveItemSound() {
        return AlternaSounds.VANISH_ITEM_FRAME_REMOVE_ITEM.get();
    }

    @Override
    public SoundEvent getBreakSound() {
        return AlternaSounds.VANISH_ITEM_FRAME_BREAK.get();
    }

    @Override
    public SoundEvent getPlaceSound() {
        return AlternaSounds.VANISH_ITEM_FRAME_PLACE.get();
    }

    @Override
    public SoundEvent getAddItemSound() {
        return AlternaSounds.VANISH_ITEM_FRAME_ADD_ITEM.get();
    }

    @Override
    public SoundEvent getRotateItemSound() {
        return AlternaSounds.VANISH_ITEM_FRAME_ROTATE_ITEM.get();
    }

    @Override
    protected ItemStack getFrameItemStack() {
        return new ItemStack(AlternaItems.VANISH_ITEM_FRAME.get());
    }
}
