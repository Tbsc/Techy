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

import net.minecraft.item.ItemStack;
import tbsc.techy.api.registry.recipe.IRecipeOutput;

import javax.annotation.Nonnull;

/**
 * API implementation of the {@link IRecipeOutput} interface, with support for a single {@link ItemStack}
 * output. If more than 1 stack needs to be returned, check {@link DualStackRecipeOutput} and other
 * implementations.
 *
 * Created by tbsc on 8/3/16.
 */
public class StackRecipeOutput implements IRecipeOutput<ItemStack> {

    /**
     * The output this instance stores. Recipes should ***NOT*** be changed EVER, only removed
     * and readded. NOT changed. Therefore it's final.
     */
    private final ItemStack output;

    /**
     * Internal constructor.
     * @param output The output stack
     */
    private StackRecipeOutput(ItemStack output) {
        this.output = output;
    }

    /**
     * Creates a new instance of this class.
     * Use this method and not the constructor to allow me to change how it behaves in the future
     * without breaking compatibility.
     * @param output The output stack
     * @return New recipe output
     */
    @Nonnull
    public static StackRecipeOutput of(ItemStack output) {
        return new StackRecipeOutput(output);
    }

    /**
     * Returns the output stack, stored in the {@link #output} field.
     * @return
     */
    @Override
    public ItemStack getObject() {
        return output;
    }

}
