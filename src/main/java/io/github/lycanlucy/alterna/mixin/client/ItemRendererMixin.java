package io.github.lycanlucy.alterna.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.lycanlucy.alterna.client.AlternaClientConfig;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
    private boolean renderTridentNormally(boolean original, @Local(argsOnly = true) ItemStack itemStack) {
        return original || itemStack.is(Items.TRIDENT) && AlternaClientConfig.redesignTrident();
    }
}
