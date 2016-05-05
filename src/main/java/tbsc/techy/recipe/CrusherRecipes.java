package tbsc.techy.recipe;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tbsc.techy.ConfigData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Recipe registry for the crusher.
 * The class itself is essentially the same as {@link PoweredFurnaceRecipes}, just
 * change to be relevant to the crusher.
 * Since there are two output, the output is stored as an {@link ImmutableTriple}, with
 * the left value as the first output, the middle value as the second output and the
 * third as a chance indicator in percentages for the second output
 *
 * Created by tbsc on 5/4/16.
 */
public class CrusherRecipes {

    private static final CrusherRecipes instance = new CrusherRecipes();
    private Map<IRecipeInput, ImmutableTriple<ItemStack, ItemStack, Integer>> recipeMap = new HashMap<>();
    private Map<ImmutableTriple<ItemStack, ItemStack, Integer>, Float> experienceMap = new HashMap<>();
    private Map<ImmutableTriple<ItemStack, ItemStack, Integer>, Integer> energyMap = new HashMap<>();

    /**
     * Used to get the instance of the class
     */
    public static CrusherRecipes instance() {
        return instance;
    }

    private CrusherRecipes() {}

    public void loadModRecipes() {
        addItemRecipe(Items.apple, new ItemStack(Items.arrow), new ItemStack(Items.baked_potato), 50, 100, 4000); // Test recipe
        addOreDictionaryRecipe("gemDiamond", new ItemStack(Items.blaze_powder), new ItemStack(Items.beef), 40, 100, 4000);
    }

    /**
     * Adds a recipe with a block as the input
     */
    public void addBlockRecipe(@Nonnull Block input, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        this.addItemRecipe(Item.getItemFromBlock(input), output, output2, output2Chance, experience, energyUsage);
    }

    /**
     * Adds a recipe with an item as the input, and 2 outputs with a chance
     */
    public void addItemRecipe(@Nonnull Item input, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        this.addItemStackRecipe(new ItemStack(input, 1, 32767), output, output2, output2Chance, experience, energyUsage);
    }

    /**
     * Adds a recipe with an ore dictionary ore name as the input, and 2 outputs with a chance
     */
    public void addOreDictionaryRecipe(@Nonnull String oreName, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        this.addIRecipeInputRecipe(OreRecipeInput.of(oreName), output, output2, output2Chance, experience, energyUsage);
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addItemStackRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        if (getSmeltingResult(StackRecipeInput.of(input)) != null) {
            FMLLog.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        ImmutableTriple<ItemStack, ItemStack, Integer> key = new ImmutableTriple<>(output, output2, output2Chance);
        this.recipeMap.put(StackRecipeInput.of(input), key);
        this.experienceMap.put(key, experience);
        this.energyMap.put(key, energyUsage);
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addIRecipeInputRecipe(IRecipeInput input, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        if (getSmeltingResult(input) != null) {
            FMLLog.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        ImmutableTriple<ItemStack, ItemStack, Integer> key = new ImmutableTriple<>(output, output2, output2Chance);
        this.recipeMap.put(input, key);
        this.experienceMap.put(key, experience);
        this.energyMap.put(key, energyUsage);
    }

    /**
     * Checks if the given item stack is a valid recipe input
     * @param input item stack to check
     * @return if is a valid input for any recipe
     */
    public boolean isValidInput(ItemStack input) {
        return getSmeltingResult(StackRecipeInput.of(input)) != null;
    }

    /**
     * Checks if the given ore name is a valid recipe input
     * @param input ore name to check
     * @return if it is a valid input for any recipe
     */
    public boolean isValidInput(String input) {
        return getSmeltingResult(OreRecipeInput.of(input)) != null;
    }

    /**
     * Returns the smelting result of an item.
     */
    public ImmutableTriple<ItemStack, ItemStack, Integer> getSmeltingResult(IRecipeInput input) {
        for (Map.Entry<IRecipeInput, ImmutableTriple<ItemStack, ItemStack, Integer>> entry : this.recipeMap.entrySet()) {
            if (this.compareIRecipeInputs(input, entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Checks if the two inputs are equal
     */
    private boolean compareIRecipeInputs(IRecipeInput input1, IRecipeInput input2) {
        if (input1.getInput() instanceof String && input2.getInput() instanceof String) {
            return ((String) input1.getInput()).equalsIgnoreCase((String) input2.getInput());
        }
        if (input1.getInput() instanceof ItemStack && input2.getInput() instanceof ItemStack) {
            ItemStack stack1 = (ItemStack) input1.getInput();
            ItemStack stack2 = (ItemStack) input2.getInput();
            return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
        }
        if (input1.getInput() instanceof ItemStack && input2.getInput() instanceof String) {
            ItemStack stack1 = (ItemStack) input1.getInput();
            String stack2 = (String) input2.getInput();
            return ItemHelper.getOreNames(stack1).contains(stack2);
        }
        if (input1.getInput() instanceof String && input2.getInput() instanceof ItemStack) {
            String stack1 = (String) input1.getInput();
            ItemStack stack2 = (ItemStack) input2.getInput();
            return ItemHelper.getOreNames(stack2).contains(stack1);
        }
        return false;
    }

    /**
     * Compares between two item stacks.
     * Checks the metadata and the item type.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    /**
     * Returns the recipe map
     * @return recipe map instance
     */
    public Map<IRecipeInput, ImmutableTriple<ItemStack, ItemStack, Integer>> getRecipeMap() {
        return this.recipeMap;
    }

    /**
     * Returns the Outputs --> Experience float map
     * @return experience map instance
     */
    public Map<ImmutableTriple<ItemStack, ItemStack, Integer>, Float> getExperienceMap() { return this.experienceMap; }

    /**
     * Returns the Outputs --> energy usage integer map
     * @return energy usage map instance
     */
    public Map<ImmutableTriple<ItemStack, ItemStack, Integer>, Integer> getEnergyMap() { return this.energyMap; }

    /**
     * Returns the amount of experience that should be given upon smelting
     * @param output first ***OUTPUT*** item
     * @param output2 second output item
     * @param output2Chance chance for second output
     * @return how much experience is given upon operation completion
     */
    public float getSmeltingExperience(ItemStack output, ItemStack output2, int output2Chance) {
        float ret = output.getItem().getSmeltingExperience(output);
        if (ret != -1) return ret;

        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Float> entry : this.experienceMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft()) && this.compareItemStacks(output2, entry.getKey().getMiddle()) && output2Chance == entry.getKey().getRight()) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }

    /**
     * Returns the amount of experience that should be given upon smelting
     * @param output ***OUTPUT*** item
     * @return how much experience is given upon operation completion
     */
    public float getSmeltingExperience(ItemStack output) {
        float ret = output.getItem().getSmeltingExperience(output);
        if (ret != -1) return ret;

        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Float> entry : this.experienceMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }

    /**
     * Returns the amount of experience that should be given upon smelting
     * @param output first ***OUTPUT*** item
     * @param output2 second output item
     * @return how much experience is given upon operation completion
     */
    public float getSmeltingExperience(ItemStack output, ItemStack output2) {
        float ret = output.getItem().getSmeltingExperience(output);
        if (ret != -1) return ret;

        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Float> entry : this.experienceMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft()) && this.compareItemStacks(output2, entry.getKey().getMiddle())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }

    /**
     * Returns the amount of energy that should be consumed upon doing this recipe.
     * @param output first ***OUTPUT*** item
     * @param output2 second output item
     * @param output2Chance chance for second output
     * @return energy usage for recipe
     */
    public int getSmeltingEnergy(ItemStack output, ItemStack output2, int output2Chance) {
        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Integer> entry : this.energyMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft()) && this.compareItemStacks(output2, entry.getKey().getMiddle()) && output2Chance == entry.getKey().getRight()) {
                return entry.getValue();
            }
        }

        return ConfigData.crusherDefaultEnergyUsage; // Default value, if nothing is found
    }

    /**
     * Returns the amount of energy that should be consumed upon doing this recipe.
     * @param output first ***OUTPUT*** item
     * @param output2 second output item
     * @return energy usage for recipe
     */
    public int getSmeltingEnergy(ItemStack output, ItemStack output2) {
        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Integer> entry : this.energyMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft()) && this.compareItemStacks(output2, entry.getKey().getMiddle())) {
                return entry.getValue();
            }
        }

        return ConfigData.crusherDefaultEnergyUsage; // Default value, if nothing is found
    }

    /**
     * Returns the amount of energy that should be consumed upon doing this recipe.
     * @param output first ***OUTPUT*** item
     * @return energy usage for recipe
     */
    public int getSmeltingEnergy(ItemStack output) {
        for (Map.Entry<ImmutableTriple<ItemStack, ItemStack, Integer>, Integer> entry : this.energyMap.entrySet()) {
            if (this.compareItemStacks(output, entry.getKey().getLeft())) {
                return entry.getValue();
            }
        }

        return ConfigData.crusherDefaultEnergyUsage; // Default value, if nothing is found
    }

}
