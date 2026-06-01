package io.github.lycanlucy.alterna.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;
import org.jetbrains.annotations.NotNull;

public abstract class AlternaSalmonModel extends HierarchicalModel<Salmon> {
    protected abstract ModelPart bodyBack();

    protected abstract ModelPart leftFin();

    protected abstract ModelPart rightFin();

    @Override
    public void setupAnim(@NotNull Salmon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float rotationMultiplier = 1.0F;
        float sineMultiplier = 1.0F;
        if (!entity.isInWater()) {
            rotationMultiplier = 1.3F;
            sineMultiplier = 1.7F;
        }

        this.bodyBack().yRot = -rotationMultiplier * 0.25F * Mth.sin(sineMultiplier * 0.6F * ageInTicks);
        this.rightFin().zRot = -0.2F + 0.4F * Mth.sin(ageInTicks * 0.2F);
        this.leftFin().zRot = 0.2F - 0.4F * Mth.sin(ageInTicks * 0.2F);
    }
}
