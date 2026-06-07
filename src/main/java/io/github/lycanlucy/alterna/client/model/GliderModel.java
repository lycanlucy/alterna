package io.github.lycanlucy.alterna.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.lycanlucy.alterna.Alterna;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class GliderModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Alterna.id("glider"), "main");
    private final ModelPart root;

    public GliderModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.root = root.getChild("root");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -19.0F, -6.0F, 16.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("right_handle_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.9163F));

        root.addOrReplaceChild("left_handle_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.9163F));

        root.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(44, 44).addBox(-10.0F, 0.0F, -6.0F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -19.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

        root.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(0, 44).addBox(0.0F, 0.0F, -6.0F, 10.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -19.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
