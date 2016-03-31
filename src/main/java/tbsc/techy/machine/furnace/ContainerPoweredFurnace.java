package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileBase;

/**
 * Created by tbsc on 3/29/16.
 */
public class ContainerPoweredFurnace extends ContainerBase {

    public ContainerPoweredFurnace(IInventory playerInv, TileBase tileBase, int tileInvSize) {
        super(playerInv, tileBase, tileInvSize);
    }

    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 52, 37));
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 112, 37));
    }

}
