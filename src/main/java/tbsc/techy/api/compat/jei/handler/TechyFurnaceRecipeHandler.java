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
