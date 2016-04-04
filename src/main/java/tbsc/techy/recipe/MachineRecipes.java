package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.ConfigData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tbsc on 4/3/16.
 */
public abstract class MachineRecipes {

    protected static ConcurrentHashMap<RecipeProperty, ItemStack> recipeItemsMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Output
    protected static ConcurrentHashMap<RecipeProperty, Integer> recipeCookTimeMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Cook time
    protected static ConcurrentHashMap<RecipeProperty, Float> recipeExperienceMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Experience points
    protected static ConcurrentHashMap<RecipeProperty, Integer> recipeEnergyUsageMap = new ConcurrentHashMap<>(); // Input + Machine Type -> Energy usage

    public static void loadVanillaRecipes() {
        for (Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
            addRecipe(RecipeMachineType.POWERED_FURNACE, entry.getKey(), entry.getValue(), ConfigData.furnaceDefaultCookTime,
                    FurnaceRecipes.instance().getSmeltingExperience(entry.getKey()), ConfigData.furnaceDefaultEnergyUsage);
        }

        // addRecipe(RecipeMachineType.POWERED_FURNACE, new ItemStack(Blocks.wool, 1, 14), new ItemStack(Items.fish), ConfigData.furnaceDefaultCookTime,
        //         1000, ConfigData.furnaceDefaultEnergyUsage); // Metadata check, should only accept RED wool
    }

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

    public static void addRecipe(RecipeMachineType machineType, ItemStack input, ItemStack output, int cookTime, float experiencePoints, int energyUsage) {
        recipeItemsMap.put(new RecipeProperty(machineType, input), output);
        recipeCookTimeMap.put(new RecipeProperty(machineType, input), cookTime);
        recipeExperienceMap.put(new RecipeProperty(machineType, input), experiencePoints);
        recipeEnergyUsageMap.put(new RecipeProperty(machineType, input), energyUsage);
        FMLLog.info(machineType.toString() + " recipe added: " + input.getDisplayName() + " with stack size of " +
                input.stackSize + " -> " + output.getDisplayName() + " with stack size of " + output.stackSize);
    }

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
