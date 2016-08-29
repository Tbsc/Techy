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

package tbsc.techy.common.machine;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import tbsc.techy.api.registry.recipe.IRecipeInput;
import tbsc.techy.api.registry.recipe.IRecipeOutput;
import tbsc.techy.common.block.BlockTechyMachine;
import tbsc.techy.common.loader.annotation.Register;
import tbsc.techy.common.registry.recipe.MachineRecipeRegistry;

/**
 * Class for registering machines to the game.
 * Mostly used to just hold the instances of the machines.
 *
 * Created by tbsc on 15/07/2016.
 */
public class Machine {

    @Register.Instance
    public static BlockTechyMachine FURNACE = new BlockTechyMachine(MachineType.FURNACE);

    public static final class GUI {

        public static final int GUI_ID_POWERED_FURNACE = 0;

    }

    public static final class Recipes {

        public static MachineRecipeRegistry<IRecipeInput<Pair<ItemStack, Integer>>, IRecipeOutput<ItemStack>> FURNACE_REGISTRY = new MachineRecipeRegistry<>();

    }

}
