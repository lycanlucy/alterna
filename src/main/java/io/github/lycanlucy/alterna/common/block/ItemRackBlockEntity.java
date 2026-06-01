package io.github.lycanlucy.alterna.common.block;

import io.github.lycanlucy.alterna.registry.AlternaBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ItemRackBlockEntity extends BlockEntity {
    private static final String ITEM_TAG = "item";
    private static final String ROTATION_TAG = "rotation";
    private ItemStack item = ItemStack.EMPTY;
    private int rotation;

    public ItemRackBlockEntity(BlockPos pos, BlockState blockState) {
        super(AlternaBlockEntities.ITEM_RACK.get(), pos, blockState);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag compoundtag = super.getUpdateTag(registries);
        if (!this.item.isEmpty()) {
            compoundtag.put(ITEM_TAG, this.item.save(registries));
        }
        compoundtag.putInt(ROTATION_TAG, this.rotation);
        return compoundtag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(ITEM_TAG)) {
            this.item = ItemStack.parse(registries, tag.getCompound(ITEM_TAG)).orElse(ItemStack.EMPTY);
        } else {
            this.item = ItemStack.EMPTY;
        }
        if (tag.contains(ROTATION_TAG)) {
            this.rotation = tag.getInt(ROTATION_TAG);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.item.isEmpty()) {
            tag.put(ITEM_TAG, this.item.save(registries));
        }
        tag.putInt(ROTATION_TAG, this.rotation);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
        setChanged();
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
        setChanged();
    }
}
