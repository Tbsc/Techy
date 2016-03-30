package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileBase;

/**
 * Created by tbsc on 3/29/16.
 */
public class ContainerPoweredFurnace extends ContainerBase {

    public ContainerPoweredFurnace(IInventory playerInv, TileBase tileBase, int tileInvSize) {
        super(playerInv, tileBase, tileInvSize);
    }

}
