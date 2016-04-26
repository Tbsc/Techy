package tbsc.techy.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.FMLLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PoweredFurnaceRecipes {

    private static final PoweredFurnaceRecipes instance = new PoweredFurnaceRecipes();
    private Map<ItemStack, ItemStack> recipeMap = new HashMap<>();
    private Map<ItemStack, Float> experienceList = new HashMap<>();

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
                    FurnaceRecipes.instance().getSmeltingExperience(input));
        }
    }

    /**
     * Adds a recipe with a block as the input
     */
    public void addBlockRecipe(Block input, ItemStack stack, float experience) {
        this.addItemRecipe(Item.getItemFromBlock(input), stack, experience);
    }

    /**
     * Adds a recipe with an item as the input
     */
    public void addItemRecipe(Item input, ItemStack stack, float experience) {
        this.addItemStackRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addItemStackRecipe(ItemStack input, ItemStack output, float experience) {
        if (getSmeltingResult(input) != null) {
            FMLLog.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        this.recipeMap.put(input, output);
        this.experienceList.put(output, experience);
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(ItemStack stack) {
        for (Entry<ItemStack, ItemStack> entry : this.recipeMap.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
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

    public Map<ItemStack, Float> getExperienceMap() { return this.experienceList; }

    /**
     * Returns the amount of experience that should be given upon smelting
     * @param stack
     * @return
     */
    public float getSmeltingExperience(ItemStack stack) {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;

        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }
}