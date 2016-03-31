package tbsc.techy.container;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tbsc.techy.tile.TileBase;

/**
 * Created by tbsc on 3/29/16.
 */
public abstract class ContainerBase extends Container {

    public IInventory playerInv;
    public TileBase tileBase;
    protected int tileInvSize;
    private int nextAvailableSlot = 0;

    public ContainerBase(IInventory playerInv, TileBase tileBase, int tileInvSize) {
        this.playerInv = playerInv;
        this.tileBase = tileBase;
        this.tileInvSize = tileInvSize;

        addBlockSlots();

        // Player Inventory, Slot 9-35, Slot IDs are dynamic
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player Inventory, Slot 0-8, Slot IDs are dynamic
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    /**
     * Solves a lot of problems with slot ID assignment, as it will make one every time (and the
     * same one!)
     * @return the next available slot
     */
    protected int getNextAvailableSlot() {
        int slotToReturn = nextAvailableSlot;
        ++nextAvailableSlot;
        return slotToReturn;
    }

    /**
     * Needs to be implemented then any slots that the machine itself needs to add, it
     * adds there. Slot IDs are handled by the {@code getNextAvailableSlot()} method,
     * which will give a slot that you can use.
     */
    protected abstract void addBlockSlots();

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileBase.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack previous = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (index < tileInvSize) {
                // From TE Inventory to Player Inventory
                if (!this.mergeItemStack(current, tileInvSize, tileInvSize + 45, true))
                    return null;
            } else {
                // From Player Inventory to TE Inventory
                if (!this.mergeItemStack(current, 0, tileInvSize, false))
                    return null;
            }

            if (current.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();

            if (current.stackSize == previous.stackSize)
                return null;
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }

}
