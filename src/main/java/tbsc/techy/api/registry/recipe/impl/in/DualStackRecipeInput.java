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
import org.apache.commons.lang3.tuple.Pair;
import tbsc.techy.api.registry.recipe.IRecipeInput;
import tbsc.techy.api.registry.recipe.IRecipeOutput;

/**
 * API implementation of the {@link IRecipeOutput} interface, that adds support for multiple stack input.
 *
 * Created by tbsc on 8/3/16.
 */
public class DualStackRecipeInput implements IRecipeInput<Pair<ItemStack, ItemStack>> {

    private Pair<ItemStack, ItemStack> input;

    public DualStackRecipeInput(Pair<ItemStack, ItemStack> input) {
        this.input = input;
    }

    public static DualStackRecipeInput of(ItemStack firstOutput, ItemStack secondOutput) {
        return new DualStackRecipeInput(Pair.of(firstOutput, secondOutput));
    }

    @Override
    public Pair<ItemStack, ItemStack> getObject() {
        return input;
    }

}
