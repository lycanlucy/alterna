package io.github.lycanlucy.alterna.common.data;

import io.github.lycanlucy.alterna.Alterna;
import io.github.lycanlucy.alterna.registry.AlternaItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class AlternaRecipeProvider extends RecipeProvider {
    public AlternaRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    private static void itemRack(RecipeOutput recipeOutput, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 4)
                .group("item_rack")
                .define('#', material)
                .pattern("###")
                .unlockedBy("has_stripped_log", has(material))
                .save(recipeOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        itemRack(recipeOutput, AlternaItems.OAK_ITEM_RACK, Items.STRIPPED_OAK_LOG);
        itemRack(recipeOutput, AlternaItems.SPRUCE_ITEM_RACK, Items.STRIPPED_SPRUCE_LOG);
        itemRack(recipeOutput, AlternaItems.BIRCH_ITEM_RACK, Items.STRIPPED_BIRCH_LOG);
        itemRack(recipeOutput, AlternaItems.JUNGLE_ITEM_RACK, Items.STRIPPED_JUNGLE_LOG);
        itemRack(recipeOutput, AlternaItems.ACACIA_ITEM_RACK, Items.STRIPPED_ACACIA_LOG);
        itemRack(recipeOutput, AlternaItems.DARK_OAK_ITEM_RACK, Items.STRIPPED_DARK_OAK_LOG);
        itemRack(recipeOutput, AlternaItems.MANGROVE_ITEM_RACK, Items.STRIPPED_MANGROVE_LOG);
        itemRack(recipeOutput, AlternaItems.CHERRY_ITEM_RACK, Items.STRIPPED_CHERRY_LOG);
        itemRack(recipeOutput, AlternaItems.BAMBOO_ITEM_RACK, Items.STRIPPED_BAMBOO_BLOCK);
        itemRack(recipeOutput, AlternaItems.CRIMSON_ITEM_RACK, Items.STRIPPED_CRIMSON_STEM);
        itemRack(recipeOutput, AlternaItems.WARPED_ITEM_RACK, Items.STRIPPED_WARPED_STEM);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, AlternaItems.GLIDER.get()).define('#', Items.PHANTOM_MEMBRANE).define('/', Items.STICK).define('S', Items.STRING).pattern("###").pattern("///").pattern("S S").unlockedBy("has_membrane", has(Items.PHANTOM_MEMBRANE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.TRIDENT).define('#', Items.PRISMARINE_SHARD).define('X', AlternaItems.SUNKEN_TRIDENT).pattern("###").pattern("#X#").pattern("###").unlockedBy("has_sunken_tridnet", has(AlternaItems.SUNKEN_TRIDENT)).save(recipeOutput, Alterna.id("trident"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, AlternaItems.VANISH_ITEM_FRAME).requires(Items.ITEM_FRAME).requires(AlternaItems.INK_BOTTLE).unlockedBy("has_item_frame", has(Items.ITEM_FRAME)).unlockedBy("has_ink_bottle", has(AlternaItems.INK_BOTTLE)).save(recipeOutput);
    }
}