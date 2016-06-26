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

package tbsc.techy.api.compat.jei;

import cofh.lib.util.helpers.ItemHelper;
import com.google.common.collect.Lists;
import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tbsc.techy.ConfigData;
import tbsc.techy.api.compat.jei.category.TechyGenericRecipeCategory;
import tbsc.techy.api.compat.jei.handler.TechyCrusherRecipeHandler;
import tbsc.techy.api.compat.jei.handler.TechyFurnaceRecipeHandler;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;
import tbsc.techy.init.LegacyInit;
import tbsc.techy.machine.crusher.BlockCrusher;
import tbsc.techy.machine.crusher.GuiCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.GuiPoweredFurnace;
import tbsc.techy.machine.generator.GuiGeneratorBase;
import tbsc.techy.machine.generator.coal.BlockCoalGenerator;
import tbsc.techy.recipe.CrusherRecipes;
import tbsc.techy.recipe.IRecipeInput;
import tbsc.techy.recipe.PoweredFurnaceRecipes;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JEI plugin for Techy.
 * This code is based on the code in the vanilla JEI plugin, as I have no clue how to do this sh*t.
 *
 * Created by tbsc on 6/4/16.
 */
@JEIPlugin
public class TechyJEIPlugin extends BlankModPlugin {

    public static final String FURNACE_UID = "techy.jei.furnace";
    public static final String CRUSHER_UID = "techy.jei.crusher";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        jeiHelpers.getNbtIgnoreList().ignoreNbtTagNames("AttributeModifiers", "CanDestroy", "CanPlaceOn", "display", "HideFlags", "RepairCost", "Unbreakable");
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        registry.addRecipeCategories(
                new TechyGenericRecipeCategory(guiHelper, FURNACE_UID, new ResourceLocation("Techy:textures/gui/container/guiPoweredFurnace.png")) {

                    IDrawableStatic progressBackground;
                    IDrawableAnimated progressBar;

                    @Override
                    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
                        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
                        guiItemStacks.init(0, true, 21, 26);
                        guiItemStacks.init(1, false, 81, 26);
                        guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs());
                        guiItemStacks.setFromRecipe(1, recipeWrapper.getOutputs());
                    }

                    @Override
                    public void drawAnimations(@Nonnull Minecraft minecraft) {
                        if (progressBar == null) {
                            IDrawableStatic staticProgressBar = guiHelper.createDrawable(new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 22, 16, 22, 16);
                            progressBar = guiHelper.createAnimatedDrawable(staticProgressBar, 300, IDrawableAnimated.StartDirection.LEFT, false);
                        }
                        if (progressBackground == null) {
                            progressBackground = guiHelper.createDrawable(new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 16);
                        }

                        progressBackground.draw(minecraft, 50, 27);
                        progressBar.draw(minecraft, 50, 27);
                    }

                },
                new TechyGenericRecipeCategory(guiHelper, CRUSHER_UID, new ResourceLocation("Techy:textures/gui/container/guiCrusher.png")) {

                    IDrawableStatic progressBackground;
                    IDrawableAnimated progressBar;

                    @Override
                    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
                        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
                        guiItemStacks.init(0, true, 11, 26);
                        guiItemStacks.init(1, false, 71, 26);
                        guiItemStacks.init(2, false, 91, 26);
                        guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs());
                        guiItemStacks.setFromRecipe(1, recipeWrapper.getOutputs().get(0));
                        guiItemStacks.setFromRecipe(2, recipeWrapper.getOutputs().get(1));
                    }

                    @Override
                    public void drawAnimations(@Nonnull Minecraft minecraft) {
                        if (progressBar == null) {
                            IDrawableStatic staticProgressBar = guiHelper.createDrawable(new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 22, 16, 22, 16);
                            progressBar = guiHelper.createAnimatedDrawable(staticProgressBar, 300, IDrawableAnimated.StartDirection.LEFT, false);
                        }
                        if (progressBackground == null) {
                            progressBackground = guiHelper.createDrawable(new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 16);
                        }

                        progressBackground.draw(minecraft, 42, 27);
                        progressBar.draw(minecraft, 42, 27);
                    }

                }
        );

        registry.addRecipeHandlers(
                new TechyFurnaceRecipeHandler(),
                new TechyCrusherRecipeHandler()
        );

        registry.addRecipeClickArea(GuiPoweredFurnace.class, 80, 37, 22, 16, FURNACE_UID);
        registry.addRecipeClickArea(GuiCrusher.class, 72, 37, 22, 16, CRUSHER_UID);

        registry.addAdvancedGuiHandlers(new TechyAdvancedGuiHandler<>(GuiPoweredFurnace.class));
        registry.addAdvancedGuiHandlers(new TechyAdvancedGuiHandler<>(GuiCrusher.class));
        registry.addAdvancedGuiHandlers(new TechyAdvancedGuiHandler<>(GuiGeneratorBase.class));

        registry.addRecipeCategoryCraftingItem(new ItemStack(BlockPoweredFurnace.instance), FURNACE_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlockCrusher.instance), CRUSHER_UID);

        registry.addDescription(Lists.newArrayList(new ItemStack(BlockPoweredFurnace.instance), new ItemStack(BlockCrusher.instance), new ItemStack(BlockCoalGenerator.instance)),
                "Techy Machines",
                "",
                "Reconfigurable sides can be configured using the tab on the right side. " +
                        "Blue means input, orange is output, gray is input AND output, and red is blocked (no transfer *at all*). " +
                "If a side is configured to either input or output, then the machine will actively try to push/pull items " +
                        "from those sides. If you just want it to allow anything, configure it to gray.",
                "",
                "Boosters (WIP)",
                "Boosters are items that modify the behaviour of some machines, and can be inserted to machines by putting " +
                        "them in one of the 4 slots on the left, outside of the machine GUI. " +
                        "While some boosters will make a machine operate better, some may make it operate worse (tier 0 boosters). " +
                        "It also depends on the machine. Energy boosters will make generators perform worse (in terms of energy generation).");

        registry.addDescription(new ItemStack(LegacyInit.blockOreCopper),
                "Copper Ore Generation",
                "",
                "Max ores per vein: " + ConfigData.copperPerVein,
                "Max ores per chunk: " + ConfigData.copperPerChunk,
                "Maximum height for copper generation: "+ ConfigData.copperMaxHeight,
                "Harvest level: " + LegacyInit.blockOreCopper.getHarvestLevel(LegacyInit.blockOreCopper.getBlockState().getBaseState()));
        registry.addDescription(new ItemStack(LegacyInit.blockOreTin),
                "Tin Ore Generation",
                "",
                "Max ores per vein: " + ConfigData.tinPerVein,
                "Max ores per chunk: " + ConfigData.tinPerChunk,
                "Maximum height for tin generation: "+ ConfigData.tinMaxHeight,
                "Harvest level: " + LegacyInit.blockOreTin.getHarvestLevel(LegacyInit.blockOreTin.getBlockState().getBaseState()));
        registry.addDescription(new ItemStack(LegacyInit.blockOreSilver),
                "Silver Ore Generation",
                "",
                "Max ores per vein: " + ConfigData.silverPerVein,
                "Max ores per chunk: " + ConfigData.silverPerChunk,
                "Maximum height for copper generation: "+ ConfigData.silverMaxHeight,
                "Harvest level: " + LegacyInit.blockOreSilver.getHarvestLevel(LegacyInit.blockOreSilver.getBlockState().getBaseState()));
        registry.addDescription(new ItemStack(LegacyInit.blockOreAluminium),
                "Aluminium Ore Generation",
                "",
                "Max ores per vein: " + ConfigData.aluminiumPerVein,
                "Max ores per chunk: " + ConfigData.aluminiumPerChunk,
                "Maximum height: "+ ConfigData.aluminiumMaxHeight,
                "Harvest level: " + LegacyInit.blockOreAluminium.getHarvestLevel(LegacyInit.blockOreAluminium.getBlockState().getBaseState()));
        registry.addDescription(new ItemStack(LegacyInit.blockOreLithium),
                "Lithium Ore Generation",
                "",
                "Max ores per vein: " + ConfigData.lithiumPerVein,
                "Max ores per chunk: " + ConfigData.lithiumPerChunk,
                "Maximum height: "+ ConfigData.lithiumMaxHeight,
                "Harvest level: " + LegacyInit.blockOreLithium.getHarvestLevel(LegacyInit.blockOreLithium.getBlockState().getBaseState()));


        List<TechyRecipeWrapper> recipes = new ArrayList<>();

        for (Map.Entry<IRecipeInput, ItemStack> entry : PoweredFurnaceRecipes.instance().getRecipeMap().entrySet()) {
            IRecipeInput input = entry.getKey();
            ItemStack inputStack;
            if (input.getInput() instanceof String) {
                inputStack = ItemHelper.getOre((String) input.getInput());
            } else {
                inputStack = (ItemStack) input.getInput();
            }
            ItemStack output = entry.getValue();
            int energyUsage = PoweredFurnaceRecipes.instance().getSmeltingEnergy(output);
            int cookTime = ConfigData.furnaceDefaultCookTime;
            recipes.add(new TechyFurnaceRecipeHandler.FurnaceRecipeWrapper(energyUsage, cookTime, Collections.singletonList(inputStack), Collections.singletonList(output)));
        }

        for (Map.Entry<IRecipeInput, ImmutableTriple<ItemStack, ItemStack, Integer>> entry : CrusherRecipes.instance().getRecipeMap().entrySet()) {
            IRecipeInput input = entry.getKey();
            ItemStack inputStack;
            if (input.getInput() instanceof String) {
                inputStack = ItemHelper.getOre((String) input.getInput());
            } else {
                inputStack = (ItemStack) input.getInput();
            }
            ImmutableTriple<ItemStack, ItemStack, Integer> output = entry.getValue();
            ItemStack firstOutput = output.getLeft();
            ItemStack secondOutput = output.getMiddle();
            int secondChance = output.getRight();
            int energyUsage = CrusherRecipes.instance().getSmeltingEnergy(firstOutput);
            int cookTime = ConfigData.crusherDefaultProcessTime;
            recipes.add(new TechyCrusherRecipeHandler.CrusherRecipeWrapper(energyUsage, cookTime, Collections.singletonList(inputStack), Lists.newArrayList(firstOutput, secondOutput), secondChance));
        }

        registry.addRecipes(recipes);
    }

}
