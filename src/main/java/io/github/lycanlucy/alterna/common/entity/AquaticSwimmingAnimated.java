package io.github.lycanlucy.alterna.common.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

public interface AquaticSwimmingAnimated {
    float alterna$getClientXRotation();

    void alterna$setClientXRotation(float rotation);

    static void applyClientXRotation(PoseStack poseStack, AquaticSwimmingAnimated entity) {
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.alterna$getClientXRotation()));
    }
}
