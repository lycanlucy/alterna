package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class AlternaRecipeProvider extends RecipeProvider {
    public AlternaRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, AlternaItems.GLIDER.get()).define('#', Items.PHANTOM_MEMBRANE).define('/', Items.STICK).define('S', Items.STRING).pattern("###").pattern("///").pattern("S S").unlockedBy("has_membrane", has(Items.PHANTOM_MEMBRANE)).save(recipeOutput);
    }
}