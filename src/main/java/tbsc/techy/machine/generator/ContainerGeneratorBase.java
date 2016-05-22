package tbsc.techy.machine.generator;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.container.slot.SlotBooster;
import tbsc.techy.tile.TileMachineBase;

/**
 * Container for the coal generator
 *
 * Created by tbsc on 5/21/16.
 */
public class ContainerGeneratorBase extends ContainerBase {

    public ContainerGeneratorBase(IInventory playerInv, TileMachineBase tileBase, int invSize) {
        super(playerInv, tileBase, invSize);
    }

    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 10, 10));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 16));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 36));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 56));
        addSlotToContainer(new SlotBooster(tileBase, getNextAvailableSlot(), -20, 76));
    }

}
