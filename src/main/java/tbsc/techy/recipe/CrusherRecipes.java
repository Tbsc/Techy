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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tbsc.techy.ConfigData;
import tbsc.techy.api.util.DevUtil;
import tbsc.techy.init.ItemInit;
import tbsc.techy.item.ItemDusts;

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
        addBlockRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), 20, 2, 3000);
        addBlockRecipe(Blocks.GRAVEL, new ItemStack(Blocks.SAND), null, 0, 2, 2800);
        addBlockRecipe(Blocks.SAND, new ItemStack(Items.CLAY_BALL), null, 0, 2, 2800);

        addOreDictionaryRecipe("ingotIron", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.IRON.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreIron", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.IRON.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockIron", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.IRON.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotGold", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.GOLD.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreGold", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.GOLD.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockGold", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.GOLD.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("gemDiamond", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.DIAMOND.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreDiamond", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.DIAMOND.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockDiamond", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.DIAMOND.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("itemCoal", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.COAL.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("itemCharcoal", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.COAL.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreCoal", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.COAL.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockCoal", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.COAL.id), null, 0, 2, 20000);
        addOreDictionaryRecipe("blockCharcoal", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.COAL.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotCopper", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.COPPER.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreCopper", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.COPPER.id), new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.GOLD.id), 30, 4, 5000);
        addOreDictionaryRecipe("blockCopper", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.COPPER.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotTin", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.TIN.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreTin", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.TIN.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockTin", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.TIN.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotSilver", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.SILVER.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreSilver", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.SILVER.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockSilver", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.SILVER.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotAluminum", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.ALUMINIUM.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreAluminum", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.ALUMINIUM.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockAluminum", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.ALUMINIUM.id), null, 0, 2, 20000);
        addOreDictionaryRecipe("ingotAluminium", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.ALUMINIUM.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreAluminium", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.ALUMINIUM.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockAluminium", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.ALUMINIUM.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotLithium", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.LITHIUM.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("oreLithium", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.LITHIUM.id), null, 0, 4, 5000);
        addOreDictionaryRecipe("blockLithium", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.LITHIUM.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("ingotBronze", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.BRONZE.id), null, 0, 2, 3000);
        addOreDictionaryRecipe("blockBronze", new ItemStack(ItemInit.itemDusts, 9, ItemDusts.DustType.BRONZE.id), null, 0, 2, 20000);

        addOreDictionaryRecipe("logWood", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.WOOD.id), null, 0, 2, 4000);

        addOreDictionaryRecipe("stone", new ItemStack(ItemInit.itemDusts, 2, ItemDusts.DustType.STONE.id), null, 0, 2, 4000);
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
        if (ItemHelper.oreProxy.oreNameExists(oreName)) {
            this.addIRecipeInputRecipe(OreRecipeInput.of(oreName), output, output2, output2Chance, experience, energyUsage);
        }
    }

    /**
     * Adds a recipe with an ItemStack as the input
     */
    public void addItemStackRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, @Nullable ItemStack output2, int output2Chance, float experience, int energyUsage) {
        if (getSmeltingResult(StackRecipeInput.of(input)) != null) {
            DevUtil.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
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
            DevUtil.info("Ignored smelting recipe with conflicting input: " + input + " = " + output);
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
