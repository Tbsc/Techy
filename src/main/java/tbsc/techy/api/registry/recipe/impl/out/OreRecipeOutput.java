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

package tbsc.techy.api.registry.recipe.impl.out;

import tbsc.techy.api.registry.recipe.IRecipeOutput;


/**
 * Ore dictionary recipe output.
 * You create an instance of this class either using {@link #OreRecipeOutput(String)} or
 * {@link #of(String)}. The string value should be the ore dictionary name that should be
 * acceptable as output.
 * All recipes are immutable, so the output field is final.
 *
 * Created by tbsc on 5/5/16.
 */
public class OreRecipeOutput<T extends String> implements IRecipeOutput<T> {

    /**
     * {@link String} field, stores the output value.
     * Recipes are immutable, so this field is final.
     */
    protected final T output;

    /**
     * Constructor for ore recipe.
     * @param output The ore name output
     */
    public OreRecipeOutput(T output) {
        this.output = output;
    }

    /**
     * Util method for creating an instance of this class.
     * @param oreName The ore name to set
     * @return Instance of this class with the ore name as value.
     */
    public static OreRecipeOutput<String> of(String oreName) {
        return new OreRecipeOutput<>(oreName);
    }

    /**
     * Returns the output value. Usually this can be anything, but in this context
     * it is always a string.
     * @return ore name output
     */
    @Override
    public T getObject() {
        return output;
    }
    

}
