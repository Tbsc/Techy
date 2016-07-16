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

/**
 * Default implementation of {@link IRecipeRegistry} that can be used for anything that holds
 * recipes in a registry.
 *
 * Created by tbsc on 15/07/2016.
 */
public class MachineRecipeRegistry<I extends IRecipeInput, O> implements IRecipeRegistry<I, O> {

    public LinkedHashMap<I, O> recipeMap = new LinkedHashMap<>();

    @Override
    public boolean hasOutput(I input) {
        return recipeMap.containsKey(input);
    }

    @Override
    public O getOutput(I input) {
        return recipeMap.get(input);
    }

}
