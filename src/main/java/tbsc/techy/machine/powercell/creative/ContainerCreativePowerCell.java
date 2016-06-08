package tbsc.techy.machine.powercell.creative;

import cofh.lib.gui.slot.SlotEnergy;
import net.minecraft.inventory.IInventory;
import tbsc.techy.machine.powercell.ContainerGenericPowerCell;
import tbsc.techy.tile.TileMachineBase;

/**
 * Re-implementation of the power cell container, for the creative power cell
 * 
 * Created by tbsc on 6/6/16.
 */
public class ContainerCreativePowerCell extends ContainerGenericPowerCell {

    public ContainerCreativePowerCell(IInventory playerInv, TileMachineBase tileBase, int tileInvSize) {
        super(playerInv, tileBase, tileInvSize);
    }

    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new SlotEnergy(tileBase, getNextAvailableSlot(), 54, 34));
    }

}
