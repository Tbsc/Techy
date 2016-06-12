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

package tbsc.techy.container;


import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import tbsc.techy.recipe.PoweredFurnaceRecipes;
import tbsc.techy.tile.TileMachineBase;

/**
 * Basic container that can be used for almost any purpose.
 * Adding slots is done though the {@code addBlockSlots} method,
 * IDs for slots are gotten using the {@code getNextAvailableSlot} method, and
 * everything else is done automatically.
 *
 * Created by tbsc on 3/29/16.
 */
public abstract class ContainerBase extends Container {

    public IInventory playerInv;
    public TileMachineBase tileBase;
    protected int tileInvSize;
    private int nextAvailableSlot = 0;

    protected ContainerBase(IInventory playerInv, TileMachineBase tileBase, int tileInvSize) {
        this.playerInv = playerInv;
        this.tileBase = tileBase;
        this.tileInvSize = tileInvSize;

        addBlockSlots();

        // Player Inventory, Slot 9-35, Slot IDs are dynamic
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs are dynamic
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    /**
     * Solves a lot of problems with slot ID assignment, as it will make one every time (and the
     * same one!)
     * @return the next available slot
     */
    protected int getNextAvailableSlot() {
        int slotToReturn = nextAvailableSlot;
        ++nextAvailableSlot;
        return slotToReturn;
    }

    /**
     * Needs to be implemented then any slots that the machine itself needs to add, it
     * adds there. Slot IDs are handled by the {@code getNextAvailableSlot()} method,
     * which will give a slot that you can use.
     */
    protected abstract void addBlockSlots();

    /**
     * Returns if the player can interact with the container.
     * As I've implemented this, it just checks with the TileEntity.
     * @param playerIn The player to be checked
     * @return if the player can interact with the container
     */
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileBase.isUseableByPlayer(playerIn);
    }

    /**
     * Transfers {@link ItemStack} in slot
     *
     * (tbh i don't understand this very much)
     *
     * @param playerIn The player
     * @param index slot
     * @return output
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack previous = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (index < tileInvSize) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, tileInvSize, tileInvSize + 36, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, tileInvSize, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }

        if (tileBase.getOutputSlots().length >= 1) {
            for (int outputSlot : tileBase.getOutputSlots()) {
                if (index == outputSlot) {
                    if (inventorySlots.get(index).getStack() != null) {
                        int experience = (int) ((tileBase.experienceModifier / 100) * PoweredFurnaceRecipes.instance().getSmeltingExperience(inventorySlots.get(index).getStack()));
                        spawnXPOrb(experience, inventorySlots.get(index).getStack().stackSize);
                    }
                }
            }
        }

        return previous;
    }

    public void spawnXPOrb(int xpAmount, int stackSize) {
        if (xpAmount == 0.0F) {
            stackSize = 0;
        } else if (xpAmount < 1.0F) {
            int j = MathHelper.floor_float((float) stackSize * xpAmount);

            if (j < MathHelper.ceiling_float_int((float) stackSize * xpAmount) && Math.random() < (double) ((float) stackSize * xpAmount - (float) j)) {
                ++j;
            }

            stackSize = j;
        }

        while (stackSize > 0) {
            int k = EntityXPOrb.getXPSplit(stackSize);
            stackSize -= k;
            tileBase.getWorld().spawnEntityInWorld(new EntityXPOrb(tileBase.getWorld(), tileBase.getPos().getX(), tileBase.getPos().getY() + 0.5D, tileBase.getPos().getZ() + 0.5D, k));
        }
    }

}
