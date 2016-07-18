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

package tbsc.techy.common.registry.recipe;

import tbsc.techy.api.registry.recipe.IRecipeInput;
import tbsc.techy.api.registry.recipe.IRecipeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Default implementation of {@link IRecipeRegistry} that can be used for anything that holds
 * recipes in a registry.
 *
 * Created by tbsc on 15/07/2016.
 */
public class MachineRecipeRegistry<I extends IRecipeInput, O> implements IRecipeRegistry<I, O> {

    /**
     * Map for the recipes.
     * Decided to use a linked hash map because then the order is kept.
     */
    public Map<I, O> recipeMap = new LinkedHashMap<>();

    /**
     * Checks if the input given exists in the recipe map
     * @param input The input to check for
     * @return recipe map contains input
     */
    @Override
    public boolean hasOutput(I input) {
        return recipeMap.containsKey(input);
    }

    /**
     * Returns the value of the input given, without checking. Null if doesn't exist.
     * @param input The input to search for
     * @return Output for input, or null
     */
    @Override
    public O getOutput(I input) {
        return recipeMap.get(input);
    }

}
