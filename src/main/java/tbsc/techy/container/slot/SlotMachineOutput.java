package tbsc.techy.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Used to prevent from players/automation putting items in the output slot, thus
 * making it slot that you're only able to extract from
 *
 * Created by tbsc on 4/26/16.
 */
public class SlotMachineOutput extends Slot {

    public SlotMachineOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * This method makes sure nothing can put items here other than the tile itself
     * @param stack item to check if valid
     * @return if the item is valid (no item is valid)
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
