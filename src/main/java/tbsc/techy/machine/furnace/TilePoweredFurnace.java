package tbsc.techy.machine.furnace;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.ConfigData;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.init.BlockInit;
import tbsc.techy.recipe.PoweredFurnaceRecipes;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
import java.util.EnumMap;

/**
 * Slot 0 - Input
 * Slot 1 - Output
 * Slot 2 - Booster slot
 * <p>
 * Packet field 1 - Energy stored
 * Packet field 2 - Progress
 * Packet field 3 - Max Progress
 * <p>
 * Created by tbsc on 4/22/16.
 */
public class TilePoweredFurnace extends TileMachineBase {

    /**
     * Stores the progress (in ticks) made for this operation.
     */
    int progress = 0;
    int totalProgress = 0;
    // int energyConsumptionPerTick = 0;
    public EnumMap<Sides, SideConfiguration> sideConfigMap = new EnumMap<>(Sides.class);

    public TilePoweredFurnace() {
        super(40000, 640, BlockPoweredFurnace.tileInvSize);

        sideConfigMap.put(Sides.FRONT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.BACK, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.LEFT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.RIGHT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.UP, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.DOWN, SideConfiguration.OUTPUT);
    }

    /**
     * Gets called every tick (1/20) of the game.
     * Used to update the tile entity, or in this case do operations, since
     * IOperator extends ITickable because operations *MUST* run every tick.
     */
    @Override
    public void update() {
        boolean markDirty = false;

        if (inventory[0] != null) {
            if (PoweredFurnaceRecipes.instance().getSmeltingResult(inventory[0]) != null && canOperate() && shouldOperate()) {
                if (!isRunning) {
                    totalProgress = ConfigData.furnaceDefaultCookTime;
                    setOperationStatus(true);
                }
                ++progress;
                if (progress >= totalProgress) {
                    progress = totalProgress = 0;
                    doOperation();
                    setOperationStatus(false);
                    markDirty = true;
                }
            }
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    /**
     * When executed, will do whatever that needs to be done in the tile.
     * In this case, smelting.
     */
    @Override
    public void doOperation() {
        // Double checking, you can never check more than enough
        if (this.canOperate()) {
            ItemStack itemstack = PoweredFurnaceRecipes.instance().getSmeltingResult(inventory[0]);
            float experience = PoweredFurnaceRecipes.instance().getSmeltingExperience(inventory[0]);

            if (this.inventory[1] == null) {
                this.inventory[1] = itemstack.copy();
            } else if (this.inventory[1].getItem() == itemstack.getItem()) {
                this.inventory[1].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            spawnXPOrb((int) experience, itemstack.stackSize);

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
    }

    /**
     * Checks if the tile can operate
     *
     * @return can the tile operate
     */
    @Override
    public boolean canOperate() {
        // No input item
        if (inventory[0] == null) {
            return false;
        } else {
            // No recipe with input item
            if (PoweredFurnaceRecipes.instance().getSmeltingResult(inventory[0]) == null) {
                return false;
            }
            // There is a recipe, then store the output in a variable
            ItemStack recipeOutput = PoweredFurnaceRecipes.instance().getSmeltingResult(inventory[0]);
            // Not enough energy stored in tile
            // if (ConfigData.furnaceDefaultEnergyUsage < getEnergyStored(EnumFacing.DOWN)) {
                // FMLLog.info("Not enough energy");
                // return false;
            // }
            // If there is no item in output slot then it can smelt, returns true
            if (inventory[1] == null) {
                return true;
            }
            // If there is an item in output slot, then check if is the output of the current recipe
            if (!inventory[1].isItemEqual(recipeOutput)) {
                return false;
            }
            // Variable of the new output slot stack size
            int result = inventory[1].stackSize + recipeOutput.stackSize;
            // Checks if the new output slot stack size respects inventory stack limit and item stack limit
            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setInteger("TotalProgress", totalProgress);

        nbt.setInteger("SideConfigFront", getConfigurationForSide(Sides.FRONT).ordinal());
        nbt.setInteger("SideConfigBack", getConfigurationForSide(Sides.BACK).ordinal());
        nbt.setInteger("SideConfigLeft", getConfigurationForSide(Sides.LEFT).ordinal());
        nbt.setInteger("SideConfigRight", getConfigurationForSide(Sides.RIGHT).ordinal());
        nbt.setInteger("SideConfigUp", getConfigurationForSide(Sides.FRONT).ordinal());
        nbt.setInteger("SideConfigDown", getConfigurationForSide(Sides.DOWN).ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        totalProgress = nbt.getInteger("TotalProgress");

        setConfigurationForSide(Sides.FRONT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigFront")));
        setConfigurationForSide(Sides.BACK, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigBack")));
        setConfigurationForSide(Sides.LEFT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigLeft")));
        setConfigurationForSide(Sides.RIGHT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigRight")));
        setConfigurationForSide(Sides.UP, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigUp")));
        setConfigurationForSide(Sides.DOWN, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigDown")));
    }

    /**
     * Returns the side config for the side given.
     * @param side the side to check
     * @return config for the side
     */
    @Override
    public SideConfiguration getConfigurationForSide(Sides side) {
        return sideConfigMap.get(side);
    }

    /**
     * Sets in the class the side config for the side given
     * @param side to change
     * @param sideConfig what to change
     */
    @Override
    public void setConfigurationForSide(Sides side, SideConfiguration sideConfig) {
        if (sideConfigMap.containsKey(side)) {
            sideConfigMap.remove(side);
        }
        sideConfigMap.put(side, sideConfig);
    }

    /**
     * For the configuration given, return the slots this configuration should access
     * @param sideConfig configuration for side
     * @return slots for configuration
     */
    @Override
    public int[] getSlotsForConfiguration(SideConfiguration sideConfig) {
        switch (sideConfig) {
            case DISABLED:
                return new int[0];
            case INPUT:
                return getInputSlots();
            case OUTPUT:
                return getOutputSlots();
            case IO:
                return ArrayUtils.addAll(getInputSlots(), getOutputSlots());
            case UNKNOWN:
                return null;
            default:
                return null;
        }
    }

    @Override
    public ResourceLocation getMachineFrontTexture() {
        return new ResourceLocation("Techy:textures/blocks/blockPoweredFurnaceFront.png");
    }

    @Override
    public int getOperationProgress() {
        return progress;
    }

    @Override
    public int getOperationTotalProgress() {
        return totalProgress;
    }

    /**
     * Used for server/client communication, for transferring int fields between sides.
     *
     * @param id ID of field to get
     * @return the value for that field, unused right now
     */
    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return getEnergyStored(EnumFacing.DOWN);
            case 1:
                return getOperationProgress();
            case 2:
                return getOperationTotalProgress();
        }
        return 0;
    }

    /**
     * Sets the value of the specified field ID to the value specified
     *
     * @param id    of field
     * @param value of field
     */
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.setEnergyStored(value);
                break;
            case 1:
                progress = value;
                break;
            case 2:
                totalProgress = value;
                break;
        }
    }

    /**
     * Returns how many field there are
     *
     * @return amount of fields
     */
    @Override
    public int getFieldCount() {
        return 0;
    }

    /**
     * Returns the slot ID array of the energy container slot
     * It needs to return an array in case there is no energy slot, therefore it should
     * return an empty array.
     *
     * @return energy container slot ID array
     */
    @Nonnull
    @Override
    public int[] getEnergySlot() {
        return new int[]{
                2
        };
    }

    /**
     * Returns the ID(s) of the input slot(s). If none, then return an empty int array.
     *
     * @return input slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[]{
                0
        };
    }

    /**
     * Returns the ID(s) of the output slot(s). If none, then return an empty int array.
     *
     * @return output slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[]{
                1
        };
    }

    /**
     * Booster items are upgrades for the machine.
     * This method should return an array of the slot IDs in which you should
     *
     * @return booster slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[0];
    } // TODO Add booster slots and support for them

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
        switch (index) {
            case 0:
                return PoweredFurnaceRecipes.instance().getSmeltingResult(stack) != null;
            case 1:
                return false;
            case 2:
                return stack.getItem() instanceof IEnergyContainerItem;
        }
        return false;
    }

    /**
     * Returns the name of the TileEntity.
     *
     * @return name of the tile
     */
    @Override
    public String getName() {
        return BlockInit.blockPoweredFurnace.getLocalizedName();
    }

    /**
     * Returns the name of the tile entity, just in a chat supported format.
     *
     * @return name of the tile
     */
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(BlockInit.blockPoweredFurnace.getLocalizedName());
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        if (frontOfBlock == side) { // FRONT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.FRONT));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.LEFT));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            return getSlotsForConfiguration(getConfigurationForSide(Sides.BACK));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.RIGHT));
        }
        if (side == EnumFacing.UP) { // UP
            return getSlotsForConfiguration(getConfigurationForSide(Sides.UP));
        }
        if (side == EnumFacing.DOWN) { // DOWN
            return getSlotsForConfiguration(getConfigurationForSide(Sides.DOWN));
        }
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        boolean sideAllows = false;
        if (frontOfBlock == side) { // FRONT
            return getConfigurationForSide(Sides.FRONT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            return getConfigurationForSide(Sides.LEFT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            return getConfigurationForSide(Sides.BACK).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            return getConfigurationForSide(Sides.RIGHT).allowsInput();
        }
        if (side == EnumFacing.UP) { // UP
            return getConfigurationForSide(Sides.UP).allowsInput();
        }
        if (side == EnumFacing.DOWN) { // DOWN
            return getConfigurationForSide(Sides.DOWN).allowsInput();
        }
        return sideAllows && ArrayUtils.contains(getOutputSlots(), index);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        boolean sideAllows = false;
        if (frontOfBlock == side) { // FRONT
            sideAllows = getConfigurationForSide(Sides.FRONT).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            sideAllows = getConfigurationForSide(Sides.LEFT).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            sideAllows = getConfigurationForSide(Sides.BACK).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            sideAllows = getConfigurationForSide(Sides.RIGHT).allowsOutput();
        }
        if (side == EnumFacing.UP) { // UP
            sideAllows = getConfigurationForSide(Sides.UP).allowsOutput();
        }
        if (side == EnumFacing.DOWN) { // DOWN
            sideAllows = getConfigurationForSide(Sides.DOWN).allowsOutput();
        }
        return sideAllows && ArrayUtils.contains(getOutputSlots(), index);
    }
}
