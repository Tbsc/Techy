package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.ConfigData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class contains all of the recipes for every machine in this mod, by adding a small
 * field to the recipe that contains the machine type for that recipe.
 *
 * Created by tbsc on 4/3/16.
 */
public abstract class MachineRecipes {

    protected static ConcurrentHashMap<RecipeProperty, ItemStack> recipeItemsMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Output
    protected static ConcurrentHashMap<RecipeProperty, Integer> recipeCookTimeMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Cook time
    protected static ConcurrentHashMap<RecipeProperty, Float> recipeExperienceMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Experience points
    protected static ConcurrentHashMap<RecipeProperty, Integer> recipeEnergyUsageMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Energy usage

    /////////////////////////
    // Recipe Manipulation //
    /////////////////////////

    /**
     * Goes to vanilla class and adds all of the vanilla furnace recipes to the powered furnace recipe registry.
     */
    public static void loadVanillaRecipes() {
        for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            addRecipe(RecipeMachineType.POWERED_FURNACE, entry.getKey(), entry.getValue(), 40,
                    FurnaceRecipes.instance().getSmeltingExperience(entry.getKey()), ConfigData.furnaceDefaultEnergyUsage);
        }

        // addRecipe(RecipeMachineType.POWERED_FURNACE, new ItemStack(Blocks.wool, 1, 14), new ItemStack(Items.fish), ConfigData.furnaceDefaultCookTime,
        //         1000, ConfigData.furnaceDefaultEnergyUsage); // Metadata check, should only accept RED wool
    }

    /**
     * Adds a recipe.
     * @param machineType The machine that'll accept this recipe
     * @param input The input of the recipe. The only things that are checked upon operating are the item and metadata. NBT is ignored.
     * @param output The output of the recipe. Output will be exactly what you put here, including metadata, damage and NBT.
     * @param cookTime The amount of time doing this recipe will require.
     * @param experiencePoints How much experience you receive upon doing this recipe
     * @param energyUsage How much energy is consumed for doing this recipe
     */
    public static void addRecipe(RecipeMachineType machineType, ItemStack input, ItemStack output, int cookTime, float experiencePoints, int energyUsage) {
        recipeItemsMap.put(new RecipeProperty(machineType, input), output);
        recipeCookTimeMap.put(new RecipeProperty(machineType, input), cookTime);
        recipeExperienceMap.put(new RecipeProperty(machineType, input), experiencePoints);
        recipeEnergyUsageMap.put(new RecipeProperty(machineType, input), energyUsage);
        FMLLog.info(machineType.toString() + " recipe added: " + input.getDisplayName() + " with stack size of " +
                input.stackSize + " -> " + output.getDisplayName() + " with stack size of " + output.stackSize);
    }

    //////////
    // Util //
    //////////

    /**
     * Loops through the Input --> Output recipe map and checks if any match is found,
     * if one is found then it return the output stack of the recipe.
     * @param machineType in which machine the recipe applies
     * @param input the input of the recipe
     * @return the output of the recipe (if found!)
     */
    public static ItemStack getOutputStack(RecipeMachineType machineType, ItemStack input) {
        if (input == null) return null; // No item in input slot
        for (RecipeProperty dataKey : recipeItemsMap.keySet()) {
            if (dataKey.machineType == machineType) {
                if (compareItemStacks(dataKey.inputStack, input)) {
                    return recipeItemsMap.get(dataKey);
                }
            }
        }
        return null; // Nothing found
    }

    /**
     * Loops through the Input --> Cook Time recipe map for a match in the input stack, and
     * return the cook time for that specific input item.
     * @param machineType What machine to check for recipes in
     * @param input The ItemStack input
     * @return the cook time for that input, 0 if not found
     */
    public static int getCookTime(RecipeMachineType machineType, ItemStack input) {
        if (input == null) return 0; // No item in input slot
        for (RecipeProperty dataKey : recipeItemsMap.keySet()) {
            if (dataKey.machineType == machineType) {
                if (compareItemStacks(dataKey.inputStack, input)) {
                    return recipeCookTimeMap.get(dataKey);
                }
            }
        }
        return 0; // Nothing found
    }

    /**
     * Checks the recipe registry for any recipe containing this input item
     * and returns a boolean value about it.
     * @param machineType What machine is the recipe for
     * @param input ItemStack in check
     * @return is there any recipe with this input item
     */
    public static boolean checkRecipeWithInput(RecipeMachineType machineType, ItemStack input) {
        if (input == null) return false; // No input item!
        for (RecipeProperty dataKey : recipeItemsMap.keySet()) { // Loop through Input --> Output recipe map
            if (dataKey.machineType == machineType) { // Compare machineType
                if (compareItemStacks(dataKey.inputStack, input)) { // Then compare input items
                    return true; // Everything is true, then recipe is found!
                }
            }
        }
        return false; // Nothing was found, false
    }

    /**
     * Compares the item type and metadata between two ItemStack.
     * @param stack1 First stack
     * @param stack2 Second stack
     * @return whether item+metadata between those two items are equal
     */
    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && stack1.getMetadata() == stack2.getMetadata();
    }

    public enum RecipeMachineType {

        POWERED_FURNACE

    }

    private static class RecipeProperty {

        public RecipeMachineType machineType;
        public ItemStack inputStack;

        public RecipeProperty(RecipeMachineType machineType, ItemStack inputStack) {
            this.machineType = machineType;
            this.inputStack = inputStack;
        }

    }

}
