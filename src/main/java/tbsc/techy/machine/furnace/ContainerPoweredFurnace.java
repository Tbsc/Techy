package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileBase;

/**
 * Container for the powered furnace. All it does is add the furnace's specific
 * slots to the container.
 *
 * Created by tbsc on 3/29/16.
 */
public class ContainerPoweredFurnace extends ContainerBase {

    public ContainerPoweredFurnace(IInventory playerInv, TileBase tileBase, int tileInvSize) {
        super(playerInv, tileBase, tileInvSize);
    }

    /**
     * Adds slots 0 (input, x=52, y=37) and 1 (output, x=112, y=37) to the container
     */
    @Override
    protected void addBlockSlots() {
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 52, 37));
        addSlotToContainer(new Slot(tileBase, getNextAvailableSlot(), 112, 37));
    }

}
