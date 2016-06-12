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

package tbsc.techy.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Used to prevent from players/automation putting items in the output slot, thus
 * making it slot that you're only able to extract from
 *
 * Created by tbsc on 4/26/16.
 */
public class SlotMachineOutput extends Slot {

    public SlotMachineOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * This method makes sure nothing can put items here other than the tile itself
     * @param stack item to check if valid
     * @return if the item is valid (no item is valid)
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
