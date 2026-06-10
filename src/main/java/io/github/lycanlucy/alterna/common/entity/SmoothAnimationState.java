package io.github.lycanlucy.alterna.common.entity;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

// Based onhttps://github.com/platypushasnohat/Unusual-Prehistory-2/blob/main/src/main/java/com/barlinc/unusual_prehistory/entity/utils/SmoothAnimationState.java
public class SmoothAnimationState extends AnimationState {
    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    public final float lerpSpeed;
    public float factorOld;
    public float factor;

    public SmoothAnimationState(float lerpSpeed) {
        this.lerpSpeed = lerpSpeed;
    }

    public SmoothAnimationState() {
        this(0.5F);
    }

    @Override
    public void animateWhen(boolean condition, int tickCount) {

        float target = condition ? 1.0F : 0.0F;

        this.factorOld = this.factor;
        this.factor += (target - this.factor) * this.lerpSpeed;

        this.factor = Mth.clamp(this.factor, 0.0F, 1.0F);

        if (condition) {
            this.startIfStopped(tickCount);
        } else if (this.factor < 0.001F) {
            this.stop();
        }
    }

    public float factor(float partialTicks) {
        return Mth.lerp(partialTicks, this.factorOld, this.factor);
    }

    public boolean isActive(float partialTicks) {
        return this.factor(partialTicks) > 0.05F;
    }

    public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float partialTicks) {
        this.animate(model, definition, ageInTicks, partialTicks, 1.0F);
    }

    public void animate(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float partialTicks, float speed) {
        float factor = this.factor(partialTicks);
        if (factor < 0.05F) {
            return;
        }
        this.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(model, definition, this.getAccumulatedTime(), factor, ANIMATION_VECTOR_CACHE);
    }

    public void animateIdle(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor, float speed, SmoothAnimationState... states) {
        this.animateIdle(model, definition, ageInTicks, partialTicks, limbSwingAmount, animationScaleFactor, 0.01F, speed, states);
    }

    public void animateIdle(HierarchicalModel<?> model, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor, float threshold, float speed, SmoothAnimationState... states) {

        float totalFactor = 1.0F;
        float extraFactor = 0.0F;

        for (SmoothAnimationState state : states) {
            float factor = state.factor(partialTicks);
            totalFactor *= 1.0F - factor;
            extraFactor += factor;
        }

        float limb = Math.min((limbSwingAmount * (totalFactor + extraFactor)) * animationScaleFactor, 1.0F);
        float factor = Math.max(this.factor(partialTicks) * (1.0F - limb), threshold);

        if (factor < 0.05F) {
            return;
        }

        this.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(model, definition, this.getAccumulatedTime(), factor, ANIMATION_VECTOR_CACHE);
    }
}
