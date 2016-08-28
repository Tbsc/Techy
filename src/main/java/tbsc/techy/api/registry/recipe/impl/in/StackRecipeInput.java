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

package tbsc.techy.api.registry.recipe.impl.in;

import net.minecraft.item.ItemStack;
import tbsc.techy.api.registry.recipe.IRecipeInput;

/**
 * Created as a wrapper for recipe inputs, to allow support for ore dictionary inputs.
 * All recipes are immutable, so input is final.
 *
 * Created by tbsc on 5/5/16.
 */
public class StackRecipeInput<T extends ItemStack> implements IRecipeInput {

    /**
     * {@link ItemStack} field, stores the input value.
     * Recipes are immutable, so this field is final.
     */
    protected final T input;

    /**
     * Constructor for item stack recipe.
     * @param input The input stack
     */
    public StackRecipeInput(T input) {
        this.input = input;
    }

    /**
     * Util method for creating an instance of this class.
     * @param data The input stack
     * @return Instance with the input stack set
     */
    public static StackRecipeInput<ItemStack> of(ItemStack data) {
        return new StackRecipeInput<>(data);
    }

    /**
     * Returns the input of this recipe. In this context, always an item stack
     * @return Input item stack
     */
    @Override
    public T getObject() {
        return input;
    }

}
