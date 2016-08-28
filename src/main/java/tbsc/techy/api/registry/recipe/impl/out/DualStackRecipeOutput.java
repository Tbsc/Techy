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
import org.apache.commons.lang3.tuple.Triple;
import tbsc.techy.api.registry.recipe.IRecipeOutput;

/**
 * API implementation of the {@link IRecipeOutput} interface, that adds support for multiple stack output.
 *
 * Created by tbsc on 8/3/16.
 */
public class DualStackRecipeOutput implements IRecipeOutput<Triple<ItemStack, ItemStack, Integer>> {

    private Triple<ItemStack, ItemStack, Integer> output;

    public DualStackRecipeOutput(Triple<ItemStack, ItemStack, Integer> output) {
        this.output = output;
    }

    public static DualStackRecipeOutput of(ItemStack firstOutput, ItemStack secondOutput, int secondOutputChance) {
        return new DualStackRecipeOutput(Triple.of(firstOutput, secondOutput, secondOutputChance));
    }

    @Override
    public Triple<ItemStack, ItemStack, Integer> getObject() {
        return output;
    }

}
