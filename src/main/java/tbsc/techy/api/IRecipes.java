package tbsc.techy.api;

import net.minecraft.item.ItemStack;
import tbsc.techy.recipe.RecipeData;
import tbsc.techy.recipe.RecipeInput;

import javax.annotation.Nullable;

/**
 * Implemented by classes that store recipe data.
 *
 * Created by tbsc on 4/25/16.
 */
public interface IRecipes {

    /**
     * Adds a recipe to the registry using the values given.
     * @param input Input of the recipe, will be typically stored as a key in a map.
     * @param data Data of the recipe, will be typically stored as a value in a map.
     */
    void addRecipe(RecipeInput input, RecipeData data);

    /**
     * Returns the recipe data of the specified recipe input.
     * @param input key for the recipe data
     * @return data for the input's recipe (can be null if recipe's not found)
     */
    @Nullable
    RecipeData getRecipeData(RecipeInput input);

    /**
     * Same thing as {@code getRecipeData(RecipeInput)}, but instead of searching by a
     * {@link RecipeInput}, it's using an {@link ItemStack}.
     * @param input key for the recipe data
     * @return data for the input's recipe (can be null if recipe's not found)
     */
    @Nullable
    RecipeData getRecipeData(ItemStack input);

    /**
     * Returns the output stack of the input given
     * @param input key to search for
     * @return output stack for the input
     */
    @Nullable
    ItemStack getOutputStack(RecipeInput input);

    /**
     * Same as {@code getOutputStack(RecipeInput)}, just using an ItemStack.
     * @param input key to search for
     * @return output stack for the input
     */
    @Nullable
    ItemStack getOutputStack(ItemStack input);

    /**
     * Returns the amount of energy that is consumed upon doing the recipe containing
     * the input as an input.
     * @param input of the recipe
     * @return energy to be used
     */
    int getEnergyUsage(RecipeInput input);

    /**
     * Same as {@code getEnergyUsage(RecipeInput)}, but with an ItemStack
     * @param input of the recipe
     * @return energy to be used
     */
    int getEnergyUsage(ItemStack input);

    /**
     * Returns the amount of time this recipe needs to be processed.
     * @param input of the recipe
     * @return amount of processing time
     */
    int getProcessTime(RecipeInput input);

    /**
     * Same as {@code getProcessTime(RecipeInput)}, but with an ItemStack
     * @param input of the recipe
     * @return amount of processing time
     */
    int getProcessTime(ItemStack input);

}
