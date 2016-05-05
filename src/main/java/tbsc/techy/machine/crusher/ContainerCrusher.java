package tbsc.techy.machine.crusher;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.container.slot.SlotBooster;
import tbsc.techy.container.slot.SlotMachineOutput;
import tbsc.techy.tile.TileMachineBase;

/**
 * Container for the crusher machine
 *
 * Created by tbsc on 5/4/16.
 */
public class ContainerCrusher extends ContainerBase {

    public ContainerCrusher(IInventory playerInv, TileMachineBase tileBase) {
        super(playerInv, tileBase, BlockCrusher.tileInvSize);
    }

    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 42, 37));
        addSlotToContainer(new SlotMachineOutput(tileBase, getNextAvailableSlot(), 102, 37));
        addSlotToContainer(new SlotMachineOutput(tileBase, getNextAvailableSlot(), 122, 37));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 16));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 36));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 56));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 76));
    }

}
