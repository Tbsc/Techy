package tbsc.techy.api.compat.jei.handler;

import mezz.jei.api.recipe.IRecipeWrapper;
import tbsc.techy.api.compat.jei.TechyJEIPlugin;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Same reason for this class as {@link TechyFurnaceRecipeHandler}, I have to use different classes
 * for each handler
 *
 * Created by tbsc on 6/5/16.
 */
public class TechyCrusherRecipeHandler extends TechyGenericRecipeHandler<TechyCrusherRecipeHandler.CrusherRecipeWrapper> {

    public TechyCrusherRecipeHandler() {
        super(TechyJEIPlugin.CRUSHER_UID);
    }

    @Nonnull
    @Override
    public Class<CrusherRecipeWrapper> getRecipeClass() {
        return CrusherRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CrusherRecipeWrapper recipeWrapper) {
        return recipeWrapper;
    }

    @Override
    public boolean isRecipeValid(@Nonnull CrusherRecipeWrapper recipeWrapper) {
        return recipeWrapper.getInputs().size() == 1 && recipeWrapper.getOutputs().size() == 2 &&
                recipeWrapper.getFluidInputs().isEmpty() && recipeWrapper.getFluidOutputs().isEmpty();
    }

    public static class CrusherRecipeWrapper extends TechyRecipeWrapper {

        public CrusherRecipeWrapper(int energyUsage, int cookTime, List inputStacks, List outputStacks) {
            super(energyUsage, cookTime, inputStacks, outputStacks, Collections.emptyList(), Collections.emptyList());
        }

    }

}
