package io.github.lycanlucy.alterna.client.model;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;
import org.jetbrains.annotations.NotNull;

public class OceanSalmonModel extends HierarchicalModel<Salmon> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Alterna.modId("ocean_salmon"), "main");
    private final ModelPart root;
    protected final ModelPart bodyBack;
    protected final ModelPart leftFin;
    protected final ModelPart rightFin;

    public OceanSalmonModel(ModelPart root) {
        this.root = root;
        this.bodyBack = root.getChild("body_back");
        this.leftFin = root.getChild("fin_left");
        this.rightFin = root.getChild("fin_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body_front", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -4.5F, 3.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -3.5F));

        PartDefinition body_back = partdefinition.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(14, 15).addBox(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 1.0F));

        body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        partdefinition.addOrReplaceChild("fin_top_front", CubeListBuilder.create().texOffs(22, 25).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, -0.5F));

        partdefinition.addOrReplaceChild("fin_top_back", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 2.5F));

        PartDefinition fin_left = partdefinition.addOrReplaceChild("fin_left", CubeListBuilder.create(), PartPose.offset(-1.5F, 23.0F, -1.0F));

        fin_left.addOrReplaceChild("fin_left_r1", CubeListBuilder.create().texOffs(24, 8).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition fin_right = partdefinition.addOrReplaceChild("fin_right", CubeListBuilder.create(), PartPose.offset(1.5F, 23.0F, -1.0F));

        fin_right.addOrReplaceChild("fin_right_r1", CubeListBuilder.create().texOffs(14, 25).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -2.0F, -0.25F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -10.75F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Salmon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float rotationMultiplier = 1.0F;
        float sineMultiplier = 1.0F;
        if (!entity.isInWater()) {
            rotationMultiplier = 1.3F;
            sineMultiplier = 1.7F;
        }

        this.bodyBack.yRot = -rotationMultiplier * 0.25F * Mth.sin(sineMultiplier * 0.6F * ageInTicks);
        this.rightFin.zRot = -0.2F + 0.4F * Mth.sin(ageInTicks * 0.2F);
        this.leftFin.zRot = 0.2F - 0.4F * Mth.sin(ageInTicks * 0.2F);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}