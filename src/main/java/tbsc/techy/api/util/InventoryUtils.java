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

package tbsc.techy.api.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Utilities for interacting with inventories and altering their contents
 *
 * Created by tbsc on 6/11/16.
 */
public class InventoryUtils {

    /**
     * Loops through the array of slot IDs given and returns the first one that can be inserted to
     * @param inv to check
     * @param slots array of slots to check if can be inserted to
     * @return First available slot, if none -1
     */
    public static int getFirstAvailableSlot(IInventory inv, int[] slots) {
        for (int slot : slots) {
            ItemStack slotItem = inv.getStackInSlot(slot);
            if (slotItem == null) {
                return slot;
            } else if (slotItem.stackSize < slotItem.getMaxStackSize()) {
                return slot;
            }
        }
        return -1;
    }

    /**
     * This method returns an array of all of the slots in the inventory given.
     * @param inv The inventory
     * @return int array of all slots
     */
    public static int[] getSlotArray(IInventory inv) {
        int[] slots = new int[inv.getSizeInventory()];
        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) {
            ArrayUtils.add(slots, slot);
        }
        return slots;
    }

    /**
     * Returns the first slot in the inventory given that the item can be inserted to.
     * It will check the slots in the array given, and if the item can be inserted to
     * that slot (while considering the stack size), it will return the slot. If not,
     * then it'll return -1.
     * @param inv The inventory to check
     * @param slots the slots to check
     * @param insert the item to check if can be inserted
     * @return first slot item can be inserted to
     */
    public static int getFirstAvailableSlot(IInventory inv, int[] slots, ItemStack insert) {
        for (int slot : slots) {
            ItemStack slotItem = inv.getStackInSlot(slot);
            if (slotItem == null) {
                return slot;
            } else if (slotItem.getItem() == insert.getItem()) {
                if (insert.stackSize + slotItem.stackSize >= insert.stackSize) {
                    return slot;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the first slot in the {@link IItemHandler} given that the item can be inserted to.
     * It will check the slots in the inv given, and if any item can be inserted to
     * any slot, it will return the slot. If not, then it'll return -1.
     * @param inv The inventory to check
     * @param insert stack checked
     * @return first slot item can be inserted to
     */
    public static int getFirstAvailableSlot(IItemHandler inv, ItemStack insert) {
        for (int slot = 0; slot < inv.getSlots(); ++slot) {
            ItemStack slotItem = inv.getStackInSlot(slot);
            if (slotItem == null) {
                return slot;
            } else if (slotItem.getItem() == insert.getItem()) {
                if (insert.stackSize + slotItem.stackSize >= insert.stackSize) {
                    return slot;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the first slot that ISN'T empty. If you need an empty slot, use {@link #getFirstAvailableSlot(IInventory, int[])}.
     * @param inv to check
     * @param slots to check
     * @return first slot which has something in it, -1 if the whole inv is empty
     */
    public static int getFirstNonEmptySlot(IInventory inv, int[] slots) {
        for (int slot : slots) {
            ItemStack slotItem = inv.getStackInSlot(slot);
            if (slotItem != null) {
                return slot;
            }
        }
        return -1;
    }

    /**
     * Returns the first slot that ISN'T empty. If you need an empty slot, use {@link #getFirstAvailableSlot(IInventory, int[])}.
     * @param inv to check
     * @param slots to check
     * @return first slot which has something in it, -1 if the whole inv is empty
     */
    public static int getFirstNonEmptySlot(IItemHandler inv, int[] slots) {
        for (int slot : slots) {
            ItemStack slotItem = inv.getStackInSlot(slot);
            if (slotItem != null) {
                return slot;
            }
        }
        return -1;
    }

}
