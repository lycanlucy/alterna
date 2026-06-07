package io.github.lycanlucy.alterna.common;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class EnumParams {
    public static final EnumProxy<HumanoidModel.ArmPose> GLIDER = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) (model, entity, arm) -> {
        model.leftArm.xRot = Mth.PI;
        model.leftArm.yRot = 0.01f;
        model.rightArm.xRot = Mth.PI;
        model.rightArm.yRot = -0.01f;
        model.leftArm.zRot = 0.7f;
        model.rightArm.zRot = -0.7f;
    });
}
