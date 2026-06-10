package io.github.lycanlucy.alterna.client.model;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.client.animation.OctopusAnimation;
import io.github.lycanlucy.alterna.common.entity.Octopus;
import io.github.lycanlucy.alterna.common.entity.SmoothAnimationState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

public class OctopusModel extends HierarchicalModel<Octopus> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Alterna.id("octopus"), "main");
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    private final ModelPart root;

    public OctopusModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("face", CubeListBuilder.create().texOffs(0, 23).addBox(-5.5F, -8.0F, 0.0F, 11.0F, 13.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -8.0F, -5.0F));

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -5.0F, 0.0F, 11.0F, 11.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -1.0F));

        root.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, 1.5F));

        root.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, -1.5F));

        root.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, 4.5F));

        PartDefinition tentacle4 = root.addOrReplaceChild("tentacle4", CubeListBuilder.create(), PartPose.offset(-1.5F, -3.0F, -4.5F));

        tentacle4.addOrReplaceChild("tentacle_r1", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition tentacle5 = root.addOrReplaceChild("tentacle5", CubeListBuilder.create(), PartPose.offset(-1.5F, -3.0F, -1.5F));

        tentacle5.addOrReplaceChild("tentacle_r2", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition tentacle6 = root.addOrReplaceChild("tentacle6", CubeListBuilder.create(), PartPose.offset(-1.5F, -3.0F, 1.5F));

        tentacle6.addOrReplaceChild("tentacle_r3", CubeListBuilder.create().texOffs(30, 23).addBox(-1.0F, -2.0F, -1.0F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition tentacle7 = root.addOrReplaceChild("tentacle7", CubeListBuilder.create(), PartPose.offset(-1.5F, -3.0F, 4.5F));

        tentacle7.addOrReplaceChild("tentacle_r4", CubeListBuilder.create().texOffs(30, 23).addBox(-1.0F, -2.0F, -1.0F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, 0.5F, 0.0F, 3.1416F, 0.0F));

        root.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, 0.0F, -1.5F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, -4.5F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Octopus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(OctopusAnimation.SWIM, limbSwing, limbSwingAmount, 1.0F, 1.0F);
        } else {
            this.animateWalk(OctopusAnimation.WALK, limbSwing, limbSwingAmount, 4.0F, 4.0F);
        }

        this.animateIdleSmooth(entity.idleAnimationState, OctopusAnimation.IDLE, ageInTicks, partialTicks, limbSwingAmount);
    }

    @Override
    protected void animate(AnimationState animationState, AnimationDefinition definition, float ageInTicks) {
        this.animate(animationState, definition, ageInTicks, 1.0F);
    }

    @Override
    protected void animateWalk(AnimationDefinition definition, float limbSwing, float limbSwingAmount, float maxAnimationSpeed, float animationScaleFactor) {
        if (limbSwingAmount < 0.01F || limbSwing < 0.01F) {
            return;
        }
        long i = (long) (limbSwing * 50.0F * maxAnimationSpeed);
        float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
        KeyframeAnimations.animate(this, definition, i, f, ANIMATION_VECTOR_CACHE);
    }

    protected void animate(AnimationState animationState, AnimationDefinition definition, float ageInTicks, float speed) {
        if (!animationState.isStarted()) {
            return;
        }
        animationState.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(this, definition, animationState.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, 1.5F, 1.0F);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, animationScaleFactor, 1.0F);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor, float speed) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, animationScaleFactor, speed);
    }

    protected void animateSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks) {
        this.animateSmooth(animationState, definition, ageInTicks, partialTicks, 1.0F);
    }

    protected void animateSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float speed) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animate(this, definition, ageInTicks, partialTicks, speed);
    }
}
