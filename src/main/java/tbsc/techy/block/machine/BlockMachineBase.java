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

package tbsc.techy.block.machine;

import net.minecraft.block.material.Material;
import tbsc.techy.block.BlockBase;
import tbsc.techy.block.BlockBaseMachine;

/**
 * NOTE: This is a REAL block, and a crafting component. For the base class,
 * look at {@link BlockBaseMachine}.
 *
 * Created by tbsc on 5/6/16.
 */
public class BlockMachineBase extends BlockBase {

    protected BlockMachineBase(String unlocalizedName) {
        super(Material.CIRCUITS, unlocalizedName);
        setHardness(5.0F);
    }

}
