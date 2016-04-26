package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IRecipes;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Recipe registry class for the powered furnace block.
 * Imports values from vanilla furnace.
 * If any documentation is missing, that's because it's already in {@link IRecipes}
 *
 * Created by tbsc on 4/25/16.
 */
public class PoweredFurnaceRecipes implements IRecipes {

    public static PoweredFurnaceRecipes instance;

    private Map<RecipeInput, RecipeData> recipeMap;

    public static void init() {
        instance = new PoweredFurnaceRecipes();
        instance.recipeMap = new HashMap<>();
        instance.loadVanillaRecipes();
    }

    private void loadVanillaRecipes() {
        for (ItemStack input : FurnaceRecipes.instance().getSmeltingList().keySet()) {
            for (ItemStack output : FurnaceRecipes.instance().getSmeltingList().values()) {
                addRecipe(RecipeInput.createItemStackInput(input.copy()), RecipeData.createRecipeData(output.copy(),
                        ConfigData.furnaceDefaultEnergyUsage, ConfigData.furnaceDefaultCookTime));
            }
        }
    }

    @Override
    public void addRecipe(RecipeInput input, RecipeData data) {
        recipeMap.put(input, data);
    }

    @Override
    public Map<RecipeInput, RecipeData> getRecipeMap() {
        return recipeMap;
    }

    @Nullable
    @Override
    public RecipeData getRecipeData(RecipeInput input) {
        if (recipeMap.containsKey(input)) {
            return recipeMap.get(input);
        }
        return null;
    }

    @Nullable
    @Override
    public RecipeData getRecipeData(ItemStack input) {
        if (recipeMap.containsKey(RecipeInput.createItemStackInput(input))) {
            return recipeMap.get(RecipeInput.createItemStackInput(input));
        }
        return null;
    }

    @Override
    public ItemStack getOutputStack(RecipeInput input) {
        if (recipeMap.containsKey(input)) {
            return recipeMap.get(input).output;
        }
        return null;
    }

    @Nullable
    @Override
    public ItemStack getOutputStack(ItemStack input) {
        if (recipeMap.containsKey(RecipeInput.createItemStackInput(input))) {
            return recipeMap.get(RecipeInput.createItemStackInput(input)).output;
        }
        return null;
    }

    @Override
    public int getEnergyUsage(RecipeInput input) {
        if (recipeMap.containsKey(input)) {
            return recipeMap.get(input).energyUsage;
        }
        return 0;
    }

    @Override
    public int getEnergyUsage(ItemStack input) {
        if (recipeMap.containsKey(RecipeInput.createItemStackInput(input))) {
            return recipeMap.get(RecipeInput.createItemStackInput(input)).energyUsage;
        }
        return 0;
    }

    @Override
    public int getProcessTime(RecipeInput input) {
        if (recipeMap.containsKey(input)) {
            return recipeMap.get(input).processTime;
        }
        return 0;
    }

    @Override
    public int getProcessTime(ItemStack input) {
        if (recipeMap.containsKey(RecipeInput.createItemStackInput(input))) {
            return recipeMap.get(RecipeInput.createItemStackInput(input)).processTime;
        }
        return 0;
    }

}
