package tbsc.techy.api.compat.jei;

import cofh.lib.util.helpers.ItemHelper;
import mezz.jei.api.*;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.ConfigData;
import tbsc.techy.api.compat.jei.category.TechyGenericRecipeCategory;
import tbsc.techy.api.compat.jei.handler.TechyCrusherRecipeHandler;
import tbsc.techy.api.compat.jei.handler.TechyFurnaceRecipeHandler;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;
import tbsc.techy.init.BlockInit;
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

                    IDrawableAnimated progressBar;
                    IDrawableAnimated energyBar;

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
                        if (energyBar == null) {
                            IDrawableStatic staticProgressBar = guiHelper.createDrawable(new ResourceLocation("cofh:textures/gui/elements/Energy.png"), 18, 0, 16, 42);
                            progressBar = guiHelper.createAnimatedDrawable(staticProgressBar, 300, IDrawableAnimated.StartDirection.BOTTOM, false);
                        }

                        progressBar.draw(minecraft, 50, 27);
                        // energyBar.draw(minecraft, getBackground().getWidth() - 24, 22);
                    }

                },
                new TechyGenericRecipeCategory(guiHelper, CRUSHER_UID, new ResourceLocation("Techy:textures/gui/container/guiCrusher.png")) {

                    IDrawableAnimated progressBar;
                    IDrawableAnimated energyBar;

                    @Override
                    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
                        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
                        guiItemStacks.init(0, true, 33, 27);
                        guiItemStacks.init(1, false, 94, 27);
                        guiItemStacks.init(2, false, 114, 27);
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
                        if (energyBar == null) {
                            IDrawableStatic staticProgressBar = guiHelper.createDrawable(new ResourceLocation("cofh:textures/gui/elements/Energy.png"), 18, 0, 16, 42);
                            progressBar = guiHelper.createAnimatedDrawable(staticProgressBar, 300, IDrawableAnimated.StartDirection.BOTTOM, false);
                        }

                        progressBar.draw(minecraft, 42, 27);
                        // energyBar.draw(minecraft, getBackground().getWidth() - 24, 22);
                    }

                }
        );

        registry.addRecipeHandlers(
                new TechyFurnaceRecipeHandler(),
                new TechyCrusherRecipeHandler()
        );

        registry.addRecipeCategoryCraftingItem(new ItemStack(BlockInit.blockPoweredFurnace), FURNACE_UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlockInit.blockCrusher), CRUSHER_UID);

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

        /*
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
            recipes.add(new TechyCrusherRecipeHandler.CrusherRecipeWrapper(energyUsage, cookTime, Collections.singletonList(inputStack), Lists.newArrayList(firstOutput, secondOutput)));
        }*/


        registry.addRecipes(recipes);
    }

}
