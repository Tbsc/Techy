package tbsc.techy.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.ConfigData;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PoweredFurnaceRecipes {

    private static final PoweredFurnaceRecipes instance = new PoweredFurnaceRecipes();
    private Map<ItemStack, ItemStack> recipeMap = new HashMap<>();
    private Map<ItemStack, Float> experienceMap = new HashMap<>();
    private Map<ItemStack, Integer> energyMap = new HashMap<>();

    /**
     * Used to get the instance of the class
     */
    public static PoweredFurnaceRecipes instance() {
        return instance;
    }

    private PoweredFurnaceRecipes() {}

    /**
     * Loads all of the vanilla recipes.
     */
    public void loadVanillaRecipes() {
        for (ItemStack input : FurnaceRecipes.instance().getSmeltingList().keySet()) {
            addItemStackRecipe(input, FurnaceRecipes.instance().getSmeltingResult(input),
                    FurnaceRecipes.instance().getSmeltingExperience(input), ConfigData.furnaceDefaultEnergyUsage);
        }
    }

    /**
     * Adds a recipe with a block as the input
     */
    public void addBlockRecipe(Block input, ItemStack stack, float experience, int energyUsage) {
        this.addItemRecipe(Item.getItemFromBlock(input), stack, experience, energyUsage);
    }

    /**
     * Adds a recipe with an item as the input
     */
    public void addItemRecipe(Item input, ItemStack stack, float experience, int energyUsage) {
        this.addItemStackRecipe(new ItemStack(input, 1, 32767), stack, experience, energyUsage);
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addItemStackRecipe(ItemStack input, ItemStack output, float experience, int energyUsage) {
        if (getSmeltingResult(input) != null) {
            FMLLog.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        this.recipeMap.put(input, output);
        this.experienceMap.put(output, experience);
        this.energyMap.put(output, energyUsage);
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(ItemStack input) {
        for (Entry<ItemStack, ItemStack> entry : this.recipeMap.entrySet()) {
            if (this.compareItemStacks(input, entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Checks if the two itemstacks are equal
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    /**
     * Returns the recipe map
     * @return recipe map instance
     */
    public Map<ItemStack, ItemStack> getRecipeMap() {
        return this.recipeMap;
    }

    /**
     * Returns the Input --> Experience float map
     * @return experience map instance
     */
    public Map<ItemStack, Float> getExperienceMap() { return this.experienceMap; }

    /**
     * Returns the Input --> energy usage integer map
     * @return energy usage map instance
     */
    public Map<ItemStack, Integer> getEnergyMap() { return this.energyMap; }

    /**
     * Returns the amount of experience that should be given upon smelting
     * @param output ***OUTPUT*** item
     * @return how much experience is given upon operation completion
     */
    public float getSmeltingExperience(ItemStack output) {
        float ret = output.getItem().getSmeltingExperience(output);
        if (ret != -1) return ret;

        for (Entry<ItemStack, Float> entry : this.experienceMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }

    /**
     * Returns the amount of energy that should be consumed upon doing this recipe.
     * @param output ***OUTPUT*** item
     * @return energy usage for recipe
     */
    public int getSmeltingEnergy(ItemStack output) {
        for (Entry<ItemStack, Integer> entry : this.energyMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey())) {
                return entry.getValue();
            }
        }

        return ConfigData.furnaceDefaultEnergyUsage; // Default value, if nothing is found
    }
}