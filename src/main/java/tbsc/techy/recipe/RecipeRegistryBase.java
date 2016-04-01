package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;
import tbsc.techy.api.IMachineRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base recipe registry class, from which machines which have recipes extend and make
 * their own recipe registries.
 *
 * NOTE: ***DO NOT*** REGISTER RECIPES HERE! FAILING TO COMPLY WITH THIS REQUIREMENT MIGHT BREAK A LOT OF STUFF!
 * IF NEEDING TO REGISTER RECIPES, DO IT FROM THE MACHINES' RESPECTIVE RECIPE REGISTRY.
 *
 * Created by tbsc on 3/31/16.
 */
public abstract class RecipeRegistryBase {

    protected ConcurrentHashMap<String, IMachineRecipe> recipeMap;

    public static RecipeRegistryBase instance;

    public abstract void addRecipe(IMachineRecipe recipe);
    public abstract void removeRecipe(String identifier);
    public abstract void removeRecipe(ItemStack input);
    public abstract void removeRecipe(IMachineRecipe recipe);

    public ConcurrentHashMap<String, IMachineRecipe> getRecipeMap() { return recipeMap;  }

    @Nullable
    public ItemStack getInputStack(String identifier) {
        return recipeMap.containsKey(identifier) ? recipeMap.get(identifier).getInputStack() : null;
    }

    public List<ItemStack> getInputStack(ItemStack output) {
        List<ItemStack> inputsFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getOutputStack().equals(output)) {
                inputsFound.add(recipe.getOutputStack());
            }
        }
        return inputsFound;
    }

    @Nullable
    public ItemStack getOutputStack(String identifier) {
        return recipeMap.containsKey(identifier) ? recipeMap.get(identifier).getOutputStack() : null;
    }

    @Nullable
    public ItemStack getOutputStack(ItemStack input) { // No need for list as only 1 recipe can have a specific ItemStack as its input
        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getInputStack().equals(input)) {
                return recipe.getOutputStack();
            }
        }
        return null;
    }

    public int getExperiencePoints(String identifier) {
        return recipeMap.containsKey(identifier) ? recipeMap.get(identifier).getExperienceAmount() : 0;
    }

    public List<Integer> getExperiencePointsListFromInput(ItemStack input) {
        List<Integer> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getInputStack().equals(input)) {
                recipesFound.add(recipe.getExperienceAmount());
            }
        }
        return recipesFound;
    }

    public List<Integer> getExperiencePointsListFromOutput(ItemStack output) {
        List<Integer> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getOutputStack().equals(output)) {
                recipesFound.add(recipe.getExperienceAmount());
            }
        }
        return recipesFound;
    }

    public int getEnergyConsumption(String identifier) {
        return recipeMap.containsKey(identifier) ? recipeMap.get(identifier).getEnergyConsumption() : 0;
    }

    public List<Integer> getEnergyConsumptionListFromInput(ItemStack input) {
        List<Integer> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getInputStack().equals(input)) {
                recipesFound.add(recipe.getEnergyConsumption());
            }
        }
        return recipesFound;
    }

    public List<Integer> getEnergyConsumptionListFromOutput(ItemStack output) {
        List<Integer> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getOutputStack().equals(output)) {
                recipesFound.add(recipe.getEnergyConsumption());
            }
        }
        return recipesFound;
    }

    @Nullable
    public IMachineRecipe getRecipe(String identifier) {
        return recipeMap.get(identifier);
    }

    @Nullable
    public IMachineRecipe getRecipeFromInput(ItemStack input) { // No need for list as only 1 recipe can have a specific ItemStack as its input
        for (IMachineRecipe recipe : recipeMap.values()) {
            if (checkInputsAreMatching(input, recipe)) {
                return recipe;
            }
        }
        return null;
    }

    protected boolean checkInputsAreMatching(ItemStack input, IMachineRecipe recipe) {
        boolean nbtEquals = true;
        boolean damageEquals = true;
        boolean metadataEquals = true;
        if (recipe.getInputStack().stackSize == input.stackSize) {
            if (!recipe.shouldIgnoreInputNBT()) {
                if (!recipe.getInputStack().getTagCompound().equals(input.getTagCompound())) {
                    nbtEquals = false;
                }
            }
            if (!recipe.shouldIgnoreInputDamage()) {
                if (!(recipe.getInputStack().getItemDamage() == input.getItemDamage())) {
                    damageEquals = false;
                }
            }
            if (!recipe.shouldIgnoreInputMetadata()) {
                if (!(recipe.getInputStack().getMetadata() == input.getMetadata())) {
                    metadataEquals = false;
                }
            }

            if (nbtEquals && damageEquals && metadataEquals) {
                return true;
            }
        }
        return false;
    }

    public List<IMachineRecipe> getRecipesFromOutput(ItemStack output) {
        List<IMachineRecipe> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getOutputStack().equals(output)) {
                recipesFound.add(recipe);
            }
        }
        return recipesFound;
    }

    public List<IMachineRecipe> getRecipesFromXP(int experiencePoints) {
        List<IMachineRecipe> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getExperienceAmount() == experiencePoints) {
                recipesFound.add(recipe);
            }
        }
        return recipesFound;
    }

    public List<IMachineRecipe> getRecipesFromEnergy(int energyConsumed) {
        List<IMachineRecipe> recipesFound = new ArrayList<>();

        for (IMachineRecipe recipe : recipeMap.values()) {
            if (recipe.getEnergyConsumption() == energyConsumed) {
                recipesFound.add(recipe);
            }
        }
        return recipesFound;
    }

}
