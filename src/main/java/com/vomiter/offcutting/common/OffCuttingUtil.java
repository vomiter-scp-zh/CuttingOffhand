package com.vomiter.offcutting.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.List;

public class OffCuttingUtil {
    public static boolean hasCuttingRecipe(Level level, ItemStack stack){
        var inventory = new ItemStackHandler(1);
        inventory.insertItem(0, stack, false);
        List<RecipeHolder<CuttingBoardRecipe>> recipeList = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CUTTING.get());
        var recipeWithCorrectInput = recipeList.stream().filter(holder -> holder.value().getIngredients().stream().findFirst().map(input -> input.test(stack)).orElse(false));
        return recipeWithCorrectInput.findFirst().isPresent();
    }
}
