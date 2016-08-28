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

package tbsc.techy.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import tbsc.techy.common.tile.TileTechyBase;

/**
 * Base class for containers.
 * Adds support for adding slots here and in the GUI.
 * <p>
 * Created by tbsc on 18/07/2016.
 */
public class ContainerTechyBase<T extends TileTechyBase> extends Container {

    protected InventoryPlayer playerInv;
    protected T tile;
    protected IItemHandler tileInv;

    public ContainerTechyBase(InventoryPlayer playerInv, T tile) {
        this.playerInv = playerInv;
        this.tile = tile;
        this.tileInv = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addPlayerInventory(playerInv, 8, 103);
    }

    @Override
    public Slot addSlotToContainer(Slot slotIn) {
        return super.addSlotToContainer(slotIn);
    }

    /**
     * Tells anything that attempts to interact with the container's slots if it can.
     *
     * @param playerIn The player that attempts to interact with the container
     * @return If it can interact with it
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(tile.getPos()) <= 64;
    }

    // Code below taken from Mantle (made by boni)

    private int playerInventoryStart = -1;

    /**
     * Draws the player inventory starting at the given position
     *
     * @param playerInventory The players inventory
     * @param xCorner         Default Value: 8
     * @param yCorner         Default Value: (rows - 4) * 18 + 103
     */
    public void addPlayerInventory(InventoryPlayer playerInventory, int xCorner, int yCorner) {
        int index = 9;

        int start = this.inventorySlots.size();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlotToContainer(new Slot(playerInventory, index, xCorner + col * 18, yCorner + row * 18));
                index++;
            }
        }

        index = 0;
        for (int col = 0; col < 9; col++) {
            this.addSlotToContainer(new Slot(playerInventory, index, xCorner + col * 18, yCorner + 58));
            index++;
        }

        playerInventoryStart = start;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        // we can only support inventory <-> playerInventory
        if (playerInventoryStart < 0) {
            // so we don't do anything if no player inventory is present because we don't know what to do
            return null;
        }

        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        // slot that was clicked on not empty?
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int end = this.inventorySlots.size();

            // Is it a slot in the main inventory? (aka not player inventory)
            if (index < playerInventoryStart) {
                // try to put it into the player inventory (if we have a player inventory)
                if (!this.mergeItemStack(itemstack1, playerInventoryStart, end, true)) {
                    return null;
                }
            }
            // Slot is in the player inventory (if it exists), transfer to main inventory
            else if (!this.mergeItemStack(itemstack1, 0, playerInventoryStart, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    // Fix for a vanilla bug: doesn't take Slot.getMaxStackSize into account
    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
        boolean ret = mergeItemStackRefill(stack, startIndex, endIndex, useEndIndex);
        if (stack != null && stack.stackSize > 0) {
            ret |= mergeItemStackMove(stack, startIndex, endIndex, useEndIndex);
        }
        return ret;
    }

    // only refills items that are already present
    protected boolean mergeItemStackRefill(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
        if (stack.stackSize <= 0) {
            return false;
        }

        boolean flag1 = false;
        int k = startIndex;

        if (useEndIndex) {
            k = endIndex - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!useEndIndex && k < endIndex || useEndIndex && k >= startIndex)) {
                slot = this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null
                        && itemstack1.getItem() == stack.getItem()
                        && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack1.getMetadata())
                        && ItemStack.areItemStackTagsEqual(stack, itemstack1)
                        && this.canMergeSlot(stack, slot)) {
                    int l = itemstack1.stackSize + stack.stackSize;
                    int limit = Math.min(stack.getMaxStackSize(), slot.getItemStackLimit(stack));

                    if (l <= limit) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < limit) {
                        stack.stackSize -= limit - itemstack1.stackSize;
                        itemstack1.stackSize = limit;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (useEndIndex) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        return flag1;
    }

    // only moves items into empty slots
    protected boolean mergeItemStackMove(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex) {
        if (stack.stackSize <= 0) {
            return false;
        }

        boolean flag1 = false;
        int k;

        if (useEndIndex) {
            k = endIndex - 1;
        } else {
            k = startIndex;
        }

        while (!useEndIndex && k < endIndex || useEndIndex && k >= startIndex) {
            Slot slot = this.inventorySlots.get(k);
            ItemStack itemstack1 = slot.getStack();

            if (itemstack1 == null && slot.isItemValid(stack) && this.canMergeSlot(stack, slot)) // Forge: Make sure to respect isItemValid in the slot.
            {
                int limit = slot.getItemStackLimit(stack);
                ItemStack stack2 = stack.copy();
                if (stack2.stackSize > limit) {
                    stack2.stackSize = limit;
                    stack.stackSize -= limit;
                } else {
                    stack.stackSize = 0;
                }
                slot.putStack(stack2);
                slot.onSlotChanged();
                flag1 = true;

                if (stack.stackSize == 0) {
                    break;
                }
            }

            if (useEndIndex) {
                --k;
            } else {
                ++k;
            }
        }

        return flag1;
    }

}
