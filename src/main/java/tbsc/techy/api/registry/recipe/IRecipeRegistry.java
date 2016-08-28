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

package tbsc.techy.api.registry.recipe;

/**
 * Interface for recipe registries.
 * Follows simple design laws such as using generics for input and output, and
 * simple methods for getting input and output.
 *
 * Created by tbsc on 15/07/2016.
 */
public interface IRecipeRegistry<I extends IRecipeInput, O extends IRecipeOutput> {

    // Get methods

    /**
     * Checks if the input given is a valid recipe.
     * Usually what this should do is check if {@link #getOutput(IRecipeInput)} doesn't return null.
     * @param input The input to check for
     * @return If the input given has a recipe with it as an input
     */
    boolean hasOutput(I input);

    /**
     * Returns the output of the input given.
     * If no output was found, return null.
     * @param input The input to search for
     * @return The output for the input given
     */
    O getOutput(I input);

    /**
     * Returns the amount of energy that needs to be consumed upon doing this recipe.
     * @param input The input to search for
     * @return The energy amount consumed
     */
    int getEnergyUsage(I input);

    /**
     * Returns the total amount of time in ticks that recipes with this input need to take.
     * @param input The input
     * @return The time for operation in ticks
     */
    int getOperationTime(I input);

    // Add methods

    /**
     * Adds a recipe to the recipe registry. How this will be stored is to be determined by the
     * implementation chosen.
     * @param input The input of the recipe
     * @param output The output of the recipe
     * @param energyUsage The amount of energy used in the recipe
     * @param operationTime The amount of time the recipe takes
     */
    void addRecipe(I input, O output, int energyUsage, int operationTime);

}
