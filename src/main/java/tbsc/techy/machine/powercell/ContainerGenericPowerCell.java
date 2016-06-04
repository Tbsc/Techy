package tbsc.techy.machine.powercell;

import cofh.lib.gui.slot.SlotEnergy;
import net.minecraft.inventory.IInventory;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileMachineBase;

/**
 * Since the slots in every power cell don't change too much, a single class is enough
 *
 * Created by tbsc on 6/3/16.
 */
public class ContainerGenericPowerCell extends ContainerBase {

    public ContainerGenericPowerCell(IInventory playerInv, TileMachineBase tileBase, int tileInvSize) {
        super(playerInv, tileBase, tileInvSize);
    }

    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new SlotEnergy(tileBase, getNextAvailableSlot(), 133, 34)); // input
        addSlotToContainer(new SlotEnergy(tileBase, getNextAvailableSlot(), 27, 34));
    }

}
