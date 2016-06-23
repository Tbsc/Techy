/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.recipe;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import tbsc.techy.ConfigData;
import tbsc.techy.api.util.DevUtil;
import tbsc.techy.block.BlockOreBase;
import tbsc.techy.item.ItemDusts;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PoweredFurnaceRecipes {

    private static final PoweredFurnaceRecipes instance = new PoweredFurnaceRecipes();
    private Map<IRecipeInput, ItemStack> recipeMap = new HashMap<>();
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

    public void loadModRecipes() {
        for (ItemDusts.DustType type : ItemDusts.DustType.values()) {
            if (type.ingot != null) {
                addOreDictionaryRecipe("dust" + type.regName, type.ingot, 3, 4800);
            }
        }
        for (BlockOreBase.OreType type : BlockOreBase.OreType.values()) {
            addOreDictionaryRecipe("ore" + type.regName, type.ingot, 3, 4800);
        }
    }

    /**
     * Adds a recipe with a block as the input
     */
    public void addBlockRecipe(Block input, ItemStack output, float experience, int energyUsage) {
        this.addItemRecipe(Item.getItemFromBlock(input), output, experience, energyUsage);
    }

    /**
     * Adds a recipe with an item as the input
     */
    public void addItemRecipe(Item input, ItemStack output, float experience, int energyUsage) {
        this.addItemStackRecipe(new ItemStack(input, 1, 32767), output, experience, energyUsage);
    }

    /**
     * Adds a recipe with an ore dictionary ore name as the input, and 2 outputs with a chance
     */
    public void addOreDictionaryRecipe(@Nonnull String oreName, @Nonnull ItemStack output, float experience, int energyUsage) {
        this.addIRecipeInputRecipe(OreRecipeInput.of(oreName), output, experience, energyUsage);
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addItemStackRecipe(ItemStack input, ItemStack output, float experience, int energyUsage) {
        if (getSmeltingResult(StackRecipeInput.of(input)) != null) {
            DevUtil.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        this.recipeMap.put(StackRecipeInput.of(input), output);
        this.experienceMap.put(output, experience);
        this.energyMap.put(output, energyUsage);
    }

    /**
     * Adds a recipe with an IRecipeInput as the input
     */
    public void addIRecipeInputRecipe(IRecipeInput input, @Nonnull ItemStack output, float experience, int energyUsage) {
        if (getSmeltingResult(input) != null) {
            DevUtil.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
            return;
        }
        this.recipeMap.put(input, output);
        this.experienceMap.put(output, experience);
        this.energyMap.put(output, energyUsage);
    }

    /**
     * Returns the smelting result of an item.
     */
    public ItemStack getSmeltingResult(IRecipeInput input) {
        for (Entry<IRecipeInput, ItemStack> entry : this.recipeMap.entrySet()) {
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
     * Checks if the two itemstacks are equal
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    /**
     * Returns the recipe map
     * @return recipe map instance
     */
    public Map<IRecipeInput, ItemStack> getRecipeMap() {
        return this.recipeMap;
    }

    /**
     * Returns the Outputs --> Experience float map
     * @return experience map instance
     */
    public Map<ItemStack, Float> getExperienceMap() { return this.experienceMap; }

    /**
     * Returns the Outputs --> energy usage integer map
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