package tbsc.techy.api.compat.jei.handler;

import mezz.jei.api.recipe.IRecipeWrapper;
import tbsc.techy.api.compat.jei.TechyJEIPlugin;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Recipe handler for the powered furnace, because JEI doesn't like the same class registered twice :(
 *
 * Created by tbsc on 6/5/16.
 */
public class TechyFurnaceRecipeHandler extends TechyGenericRecipeHandler<TechyFurnaceRecipeHandler.FurnaceRecipeWrapper> {

    public TechyFurnaceRecipeHandler() {
        super(TechyJEIPlugin.FURNACE_UID);
    }

    @Nonnull
    @Override
    public Class<FurnaceRecipeWrapper> getRecipeClass() {
        return FurnaceRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull FurnaceRecipeWrapper furnaceRecipeWrapper) {
        return furnaceRecipeWrapper;
    }

    @Override
    public boolean isRecipeValid(@Nonnull FurnaceRecipeWrapper recipeWrapper) {
        return recipeWrapper.getInputs().size() == 1 && recipeWrapper.getOutputs().size() == 1 &&
                recipeWrapper.getFluidInputs().isEmpty() && recipeWrapper.getFluidOutputs().isEmpty();
    }

    public static class FurnaceRecipeWrapper extends TechyRecipeWrapper {

        public FurnaceRecipeWrapper(int energyUsage, int cookTime, List inputStacks, List outputStacks) {
            super(energyUsage, cookTime, inputStacks, outputStacks, Collections.emptyList(), Collections.emptyList());
        }

    }

}
