package tbsc.techy.machine.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import tbsc.techy.init.BlockInit;
import tbsc.techy.recipe.MachineRecipes;
import tbsc.techy.tile.TileMachineBase;

/**
 * Slot 0 - Input
 * Slot 1 - Output
 *
 * Created by tbsc on 4/22/16.
 */
public class TilePoweredFurnace extends TileMachineBase {

    private boolean isRunning;
    private boolean shouldRun;

    public TilePoweredFurnace() {
        super(40000, 640, 2);
    }

    /**
     * Gets called every tick (1/20) of the game.
     * Used to update the tile entity, or in this case do operations, since
     * IOperator extends ITickable because operations *MUST* run every tick.
     */
    @Override
    public void update() {
        // If receiving redstone signal, then prevent machine from operating
        shouldRun = worldObj.isBlockIndirectlyGettingPowered(pos) <= 0;

        if (isRunning) { // If machine is running, then change the front of the machine to "working" state
            BlockPoweredFurnace.setState(true, worldObj, pos);
        } else {
            BlockPoweredFurnace.setState(false, worldObj, pos);
        }
    }

    /**
     * When executed, will do whatever that needs to be done in the tile.
     * In this case, smelting.
     */
    @Override
    public void doOperation() {

    }

    /**
     * Checks if the tile can operate
     * @return can the tile operate
     */
    @Override
    public boolean canOperate() {
        return false;
    }

    /**
     * Should the tile operate right now
     * @return should operate
     */
    @Override
    public boolean shouldOperate() {
        return shouldRun;
    }

    /**
     * Change the field that stores the operation state
     * @param isRunning the new status
     */
    @Override
    public void setOperationStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * @return is it running
     */
    @Override
    public boolean isOperating() {
        return isRunning;
    }

    /**
     * If slot 0 (input) --> Check if {@code stack} is a valid input
     * If slot 1 (output) --> nothing can be inserted manually
     *
     * @param index slot
     * @param stack itemstack
     * @return if it can be there
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return index == 0 ? MachineRecipes.checkRecipeWithInput(MachineRecipes.RecipeMachineType.POWERED_FURNACE, stack) : index == 1 ? false : false;
    }

    /**
     * Used for server/client communication, for transferring int fields between sides.
     * @param id ID of field to get
     * @return the value for that field, unused right now
     */
    @Override
    public int getField(int id) {
        return 0;
    }

    /**
     * Sets the value of the specified field ID to the value specified
     * @param id of field
     * @param value of field
     */
    @Override
    public void setField(int id, int value) {

    }

    /**
     * Returns how many field there are
     * @return amount of fields
     */
    @Override
    public int getFieldCount() {
        return 0;
    }

    /**
     * Returns the name of the TileEntity.
     * @return name of the tile
     */
    @Override
    public String getName() {
        return BlockInit.blockPoweredFurnace.getLocalizedName();
    }

    /**
     * Returns the name of the tile entity, just in a chat supported format.
     * @return name of the tile
     */
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(BlockInit.blockPoweredFurnace.getLocalizedName());
    }

}
