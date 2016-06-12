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

package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created as a wrapper for recipe inputs, to allow support for ore dictionary inputs.
 *
 * Created by tbsc on 5/5/16.
 */
public class StackRecipeInput<T extends ItemStack> implements IRecipeInput {

    public T input;

    public StackRecipeInput(T input) {
        this.input = input;
    }

    public static StackRecipeInput<ItemStack> of(ItemStack data) {
        return new StackRecipeInput<>(data);
    }

    @Override
    public T getInput() {
        return input;
    }
}
