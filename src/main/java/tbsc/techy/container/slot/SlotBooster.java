package tbsc.techy.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tbsc.techy.api.IBoosterItem;

/**
 * Slot that only allows booster items to be put inside it.
 *
 * Created by tbsc on 5/1/16.
 */
public class SlotBooster extends Slot {

    public SlotBooster(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * Checks if an item is valid, and in this case only booster items are valid
     * @param stack to check
     * @return is instanceof {@link IBoosterItem}
     */
    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof IBoosterItem;
    }

    /**
     * Prevents from putting more than 1 booster per slot
     * @return amount of max items per slot
     */
    @Override
    public int getSlotStackLimit() {
        return 1;
    }

}
