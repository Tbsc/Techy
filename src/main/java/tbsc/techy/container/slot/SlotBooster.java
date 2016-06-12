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
import tbsc.techy.api.IBoosterItem;

/**
 * Slot that only allows booster items to be put inside it.
 *
 * Created by tbsc on 5/1/16.
 */
public class SlotBooster extends Slot {

    public SlotBooster(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * Checks if an item is valid, and in this case only booster items are valid
     * @param stack to check
     * @return is instanceof {@link IBoosterItem}
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof IBoosterItem;
    }

    /**
     * Prevents from putting more than 1 booster per slot
     * @return amount of max items per slot
     */
    @Override
    public int getSlotStackLimit() {
        return 1;
    }

}
