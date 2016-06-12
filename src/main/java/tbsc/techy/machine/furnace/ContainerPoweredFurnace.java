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

package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.container.slot.SlotBooster;
import tbsc.techy.container.slot.SlotMachineOutput;
import tbsc.techy.tile.TileMachineBase;

/**
 * Container for the powered furnace. All it does is add the furnace's specific
 * slots to the container.
 *
 * Created by tbsc on 3/29/16.
 */
public class ContainerPoweredFurnace extends ContainerBase {

    public ContainerPoweredFurnace(IInventory playerInv, TileMachineBase tileBase) {
        super(playerInv, tileBase, BlockPoweredFurnace.tileInvSize);
    }

    /**
     * Adds slots 0 (input, x=52, y=37) and 1 (output, x=112, y=37) to the container
     */
    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 52, 37));
        addSlotToContainer(new SlotMachineOutput(tileBase, getNextAvailableSlot(), 112, 37));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 16));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 36));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 56));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 76));
    }

}
