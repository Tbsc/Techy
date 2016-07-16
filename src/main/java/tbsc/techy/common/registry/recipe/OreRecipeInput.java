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

/**
 * Ore dictionary recipe input
 *
 * Created by tbsc on 5/5/16.
 */
public class OreRecipeInput<T extends String> implements IRecipeInput {

    public T input;

    public OreRecipeInput(T input) {
        this.input = input;
    }

    public static OreRecipeInput<String> of(String oreName) {
        return new OreRecipeInput<>(oreName);
    }

    @Override
    public T getInput() {
        return input;
    }
}
