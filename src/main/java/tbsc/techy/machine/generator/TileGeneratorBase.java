package tbsc.techy.machine.generator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.init.BlockInit;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
import java.util.EnumMap;

/**
 * Base tile entity for generators
 *
 * Created by tbsc on 5/20/16.
 */
public abstract class TileGeneratorBase extends TileMachineBase {

    /**
     * Works almost the same as {@link tbsc.techy.machine.furnace.TilePoweredFurnace#sideConfigMap},
     * so just check out that
     */
    public EnumMap<Sides, SideConfiguration> sideConfigMap = new EnumMap<>(Sides.class);

    protected TileGeneratorBase(int capacity, int maxReceive, int invSize, int cookTime) {
        super(capacity, maxReceive, invSize, cookTime);
        sideConfigMap.put(Sides.FRONT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.BACK, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.UP, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.DOWN, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.LEFT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.RIGHT, SideConfiguration.INPUT);
    }

    @Override
    public void update() {
        handleRedstone();
        handleEnergyItems();
        handleBoosters();
        if (handleProcessing()) {
            markDirty();
        }
    }

    /**
     * A modified copy of {@link TileMachineBase#handleProcessing()}, that instead of processing
     * items from input to output, processes them to energy.
     * @return if it should mark tile to save
     */
    @Override
    protected boolean handleProcessing() {
        boolean markDirty = false;

        for (int i : getInputSlots()) {
            if (!isRunning) {
                if (inventory[i] != null) {
                    if (canGenerateFromItem(inventory[0]) && canOperate() && shouldOperate()) {
                        double timePercentage = timeModifier / 100;
                        totalProgress = (int) timePercentage * getBurnTimeFromItem(inventory[0]);

                        // NOTE: This sets {@link #energyConsumptionPerTick} to the amount of energy to be given every tick
                        energyConsumptionPerTick = getEnergyUsage(inventory[0]);
                        BlockBaseFacingMachine.setWorkingState(true, worldObj, pos);
                        setOperationStatus(true);

                        --inventory[0].stackSize;
                        if (this.inventory[0].stackSize <= 0) {
                            this.inventory[0] = null;
                        }

                        markDirty = true;
                    } else {
                        stopOperating(true);
                        if (BlockBaseFacingMachine.getWorkingState(worldObj, pos)) {
                            BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                        }
                    }
                } else {
                    stopOperating(true);
                    if (BlockBaseFacingMachine.getWorkingState(worldObj, pos)) {
                        BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                    }
                }
            }
        }

        if (shouldOperate()) {
            if (isOperating()) {
                ++progress;

                // Not enough room for added energy
                if (energyConsumptionPerTick + getEnergyStored(EnumFacing.DOWN) <= getMaxEnergyStored(EnumFacing.DOWN)) {
                    setEnergyStored(getEnergyStored(EnumFacing.DOWN) + energyConsumptionPerTick);
                }

                if (progress >= totalProgress) {
                    doOperation();
                    BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                    progress = totalProgress = 0;
                    setOperationStatus(false);
                    markDirty = true;
                }
            }
        }

        return markDirty;
    }

    /**
     * Returns if it can generate energy from this given item
     * @param item if energy can be generated from this
     * @return if energy can be generated from it
     */
    public abstract boolean canGenerateFromItem(ItemStack item);

    /**
     * Returns the amount of time an item inputted should burn for
     * @param item to check
     * @return time this item burns for
     */
    protected abstract int getBurnTimeFromItem(ItemStack item);

    @Override
    public void doOperation() {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean canOperate() {
        if (inventory[0] == null) {
            return false;
        } else {
            if (!canGenerateFromItem(inventory[0])) {
                return false;
            }
            return getEnergyUsage(inventory[0]) + getEnergyStored(EnumFacing.DOWN) < getMaxEnergyStored(EnumFacing.DOWN);
        }
    }

    @Override
    public int getOperationProgress() {
        return progress;
    }

    @Override
    public int getOperationTotalProgress() {
        return totalProgress;
    }

    @Nonnull
    @Override
    public int[] getEnergySlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[] {
                0
        };
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[] {
                1, 2, 3, 4
        };
    }

    /**
     * Not needed with generators, so returns null
     * @param input input item stack
     * @return null
     */
    @Override
    public ItemStack getSmeltingOutput(ItemStack input) {
        return null;
    }

    /**
     * Since I don't want to need to create additional methods, I'll just
     * reuse this method as a way to know how much energy needs to be generated
     * upon putting this item in the generator.
     * @param input generation input
     * @return how much energy is given when putting this input
     */
    @Override
    public abstract int getEnergyUsage(ItemStack input);

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
            case INPUT:
                return getInputSlots();
            case OUTPUT:
                return getOutputSlots();
            case IO:
                return ArrayUtils.addAll(getInputSlots(), getOutputSlots());
            default:
                return new int[0];
        }
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
            sideAllows = getConfigurationForSide(Sides.FRONT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            sideAllows = getConfigurationForSide(Sides.LEFT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            sideAllows = getConfigurationForSide(Sides.BACK).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            sideAllows = getConfigurationForSide(Sides.RIGHT).allowsInput();
        }
        if (side == EnumFacing.UP) { // UP
            sideAllows = getConfigurationForSide(Sides.UP).allowsInput();
        }
        if (side == EnumFacing.DOWN) { // DOWN
            sideAllows = getConfigurationForSide(Sides.DOWN).allowsInput();
        }
        return sideAllows && ArrayUtils.contains(getInputSlots(), index);
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index) {
            case 0:
                return canGenerateFromItem(stack);
            case 1 | 2 | 3 | 4:
                return stack.getItem() instanceof IBoosterItem;
            default:
                return false;
        }
    }

    /**
     * Returns int value based on id given.
     * ID 0 - energy stored
     * ID 1 - max energy stored
     * @param id to check
     * @return value for id given
     */
    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return getEnergyStored(EnumFacing.DOWN);
            case 1:
                return getMaxEnergyStored(EnumFacing.DOWN);
            default:
                return 0;
        }
    }

    /**
     * Sets the value of something based on the id given
     * ID 0 - energy stored
     * ID 1 - max energy stored
     * @param id to change
     * @param value to set
     */
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                setEnergyStored(value);
                break;
            case 1:
                energyStorage.setCapacity(value);
        }
    }

    /**
     * Amount of fields available
     * @return amount of fields
     */
    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public String getName() {
        return BlockInit.blockCoalGenerator.getLocalizedName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

}
