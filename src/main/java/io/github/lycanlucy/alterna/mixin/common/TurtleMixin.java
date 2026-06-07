package io.github.lycanlucy.alterna.mixin.common;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import io.github.lycanlucy.alterna.registry.AlternaSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Turtle.class)
public abstract class TurtleMixin extends Animal implements Bucketable {
    protected TurtleMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract void setHomePos(BlockPos homePos);

    @Override
    public boolean fromBucket() {
        return true;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        if (fromBucket) {
            this.setHomePos(this.blockPosition());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, compoundTag -> compoundTag.putInt("Age", this.getAge()));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        if (tag.contains("Age")) {
            this.setAge(tag.getInt("Age"));
        }
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(AlternaItems.BABY_TURTLE_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return AlternaSounds.BUCKET_FILL_BABY_TURTLE.get();
    }
}
