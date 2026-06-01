package io.github.lycanlucy.alterna.data.server;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AlternaRecipeProvider extends RecipeProvider {
    public AlternaRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        itemRack(recipeOutput, AlternaItems.OAK_ITEM_RACK, Items.STRIPPED_OAK_LOG);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AlternaItems.TRIDENT).define('#', Items.PRISMARINE_SHARD).define('X', AlternaItems.SUNKEN_TRIDENT).pattern("###").pattern("#X#").pattern("###").unlockedBy("has_sunken_tridnet", has(AlternaItems.SUNKEN_TRIDENT)).save(recipeOutput);
    }

    private static void itemRack(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 4)
                .group("item_rack")
                .define('#', material)
                .pattern("###")
                .unlockedBy("has_stripped_log", has(material))
                .save(recipeOutput);
    }
}
