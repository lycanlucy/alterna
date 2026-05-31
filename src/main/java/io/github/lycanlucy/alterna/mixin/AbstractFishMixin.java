package io.github.lycanlucy.alterna.mixin;

import io.github.lycanlucy.alterna.registry.AlternaAttachments;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends WaterAnimal {
    protected AbstractFishMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "saveToBucketTag", at = @At("TAIL"))
    private void saveExtraBucketTag(ItemStack stack, CallbackInfo ci) {
        if (this.hasData(AlternaAttachments.MOB_VARIANT)) {
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, compoundTag -> compoundTag.putString("alterna:mob_variant", this.getData(AlternaAttachments.MOB_VARIANT).toString()));
        }
    }


    @Inject(method = "loadFromBucketTag", at = @At("TAIL"))
    private void loadExtraBucketTag(CompoundTag tag, CallbackInfo ci) {
        if (this.hasData(AlternaAttachments.MOB_VARIANT) && tag.contains("alterna:mob_variant")) {
            this.setData(AlternaAttachments.MOB_VARIANT, ResourceLocation.parse(tag.getString("alterna:mob_variant")));
        }
    }
}
