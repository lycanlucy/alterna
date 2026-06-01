package io.github.lycanlucy.alterna.client.model;

import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;

public class RiverSalmonModel extends AlternaSalmonModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Alterna.modId("river_salmon"), "main");
    private final ModelPart root;
    protected final ModelPart bodyBack;
    protected final ModelPart leftFin;
    protected final ModelPart rightFin;

    public RiverSalmonModel(ModelPart root) {
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

        partdefinition.addOrReplaceChild("fin_top_front", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, -0.5F));

        partdefinition.addOrReplaceChild("fin_top_back", CubeListBuilder.create().texOffs(6, 28).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 2.5F));

        PartDefinition fin_left = partdefinition.addOrReplaceChild("fin_left", CubeListBuilder.create(), PartPose.offset(-1.5F, 23.0F, -1.0F));

        fin_left.addOrReplaceChild("fin_left_r1", CubeListBuilder.create().texOffs(24, 8).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition fin_right = partdefinition.addOrReplaceChild("fin_right", CubeListBuilder.create(), PartPose.offset(1.5F, 23.0F, -1.0F));

        fin_right.addOrReplaceChild("fin_right_r1", CubeListBuilder.create().texOffs(14, 25).addBox(0.0F, 0.0F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 25).addBox(-1.0F, -2.0F, -2.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-1.0F, -2.0F, -0.25F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -10.75F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    protected ModelPart bodyBack() {
        return bodyBack;
    }

    @Override
    protected ModelPart leftFin() {
        return leftFin;
    }

    @Override
    protected ModelPart rightFin() {
        return rightFin;
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}