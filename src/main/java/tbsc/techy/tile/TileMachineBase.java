/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.IOperator;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.api.util.InventoryUtils;
import tbsc.techy.block.BlockBaseFacingMachine;

import javax.annotation.Nonnull;
import java.util.EnumMap;

/**
 * Basic class for machine tile entities.
 * Adds support for energy consumption and operations.
 * {@link net.minecraft.util.ITickable} is also implemented through
 * {@link IOperator}, because operations must run every tick.
 * <p>
 * Created by tbsc on 4/22/16.
 */
public abstract class TileMachineBase extends TileBase implements IEnergyHandler, IOperator {

    /**
     * Stores the progress (in ticks) made for this operation.
     */
    public int progress = 0;

    /**
     * Stores the total amount of progress the machine needs to get to in order
     * to finish operating.
     */
    public int totalProgress = 0;

    /**
     * How much energy needs to be consumed every tick, based on the cook time of
     * the recipe and amount of energy this recipe needs to operate.
     */
    public int energyConsumptionPerTick = 0;

    /**
     * Percentage of energy to be added
     * It won't change the amount of energy per tick, but rather the total amount.
     */
    public int energyModifier = 100;

    /**
     * PERCENTAGE of time to be given.
     */
    public int timeModifier = 100;

    /**
     * Percentage of exeprience to be given.
     */
    public int experienceModifier = 100;

    /**
     * The chance to get another item. The way this will work is by taking a random number
     * and seeing if it's in the range of the modifier. So, if the modifier is 2, then numbers
     * 1 and 2 will result in an additional item, and based on the number the amount of additional
     * items will be chosen. If the number is larger, then the total amount of random numbers will
     * grow (to be exact, it calculates it like so: (16 * additionalItemModifier) + (4 * additionalItemModifier)).
     * 0 is an ignored number --  if this field equals to 0 then it doesn't choose a random number.
     *
     * Note that if an additional item IS chosen to be added, and there is no room for it, then
     * it will just not give it. For example, You have 62 items in the output slot, and the output
     * of this recipe is 1 item, but suddenly you get 2 additional items. The normal recipe output
     * is added to the output slot, which makes the output slot stack size 63. There are *2*
     * additional items to be added, so what it's going to do is *delete* BOTH of the items, not
     * just put the 1 item it can put. Therefore automation (if done right) should not backlog
     * and if it does, then additional items are lost.
     */
    public int additionalItemModifier = 0;

    /**
     * Since every machine will probably have a different amount of time processing
     * materials, I need to get from subclasses the time in which they finish
     * processing.
     */
    public int machineProcessTime = 0;

    /**
     * How many items can be auto-extracted/inserted per tick
     */
    public int itemsPerTick = 4;

    /**
     * Used for {@link #stopOperating(boolean)} to prevent {@link #update()} from operating even though
     * it shouldn't
     */
    protected boolean preventOperation = false;

    /**
     * Contains all of the data for side configuration
     */
    public EnumMap<Sides, SideConfiguration> sideConfigMap = new EnumMap<>(Sides.class);

    public EnergyStorage energyStorage;
    protected boolean isRunning;
    protected boolean shouldRun = true;

    protected TileMachineBase(int capacity, int maxTransfer, int invSize, int cookTime, int itemsPerTick) {
        super(invSize);
        this.itemsPerTick = itemsPerTick;
        this.machineProcessTime = cookTime;
        this.energyStorage = new EnergyStorage(capacity, maxTransfer);
        setAllSidesDisabled();
    }

//    int countdown = 0;

    @Override
    public void update() {
//        ++countdown;
//        if (countdown >= 10) {
//            Techy.network.sendToAll(new CPacketEnergyChanged(writeToNBT(new NBTTagCompound())));
//            countdown = 0;
//        }

        if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) { // Only run if block is there
            if (handleRedstone() || handleBoosters() || handleProcessing() || handleInsertion() || handleExtraction()) {
                this.markDirty();
            }
        }
    }

    /**
     * The default side configuration is all sides disabled.
     */
    protected void setAllSidesDisabled() {
        for (Sides side : Sides.values()) {
            setConfigurationForSide(side, SideConfiguration.DISABLED);
        }
    }

    protected boolean handleBoosters() {
        int energyModifierSet = 100, timeModifierSet = 100, experienceModifierSet = 100, additionalItemModifierSet = 0;
        if (getBoosterSlots().length >= 1) {
            for (int i = 0; i < getBoosterSlots().length; ++i) {
                if (inventory[getBoosterSlots()[i]] != null) {
                    // No need to check if item is a booster, because only booster items area allowed there
                    if (inventory[getBoosterSlots()[i]].getItem() instanceof IBoosterItem) {
                        IBoosterItem booster = (IBoosterItem) inventory[getBoosterSlots()[i]].getItem();
                        int tier = inventory[getBoosterSlots()[i]].getMetadata();
                        energyModifierSet = +booster.getEnergyModifier(tier);
                        timeModifierSet = booster.getTimeModifier(tier);
                        experienceModifierSet = +booster.getExperienceModifier(tier);
                        additionalItemModifierSet = +booster.getAdditionalItemModifier(tier);
                    }
                }
            }
        }
        energyModifier = energyModifierSet;
        timeModifier = timeModifierSet;
        experienceModifier = experienceModifierSet;
        additionalItemModifier = additionalItemModifierSet;
        return false;
    }

    /**
     * Handles redstone control. If the block is powered, then stop operating
     * @return should mark dirty
     */
    protected boolean handleRedstone() {
        // If receiving redstone signal, then prevent machine from operating
        shouldRun = !(worldObj.isBlockIndirectlyGettingPowered(pos) > 0);
        return false;
    }

    /**
     * I don't want machines to extract every tick. To solve that, I put a countdown that'll extract
     * only when it finishes.
     */
    int insertionCountdown = 0;

    /**
     * Total amount of ticks it takes to finish the countdown.
     */
    int insertionCountdownTotal = 10;

    /**
     * Current count for extraction
     */
    int extractionCountdown = 0;

    /**
     * Total amount of ticks it takes to finish the countdown.
     */
    int extractionCountdownTotal = 10;

    /**
     * Attempts to insert items to this inventory from nearby inventories, based on side configuration.
     * @return should mark dirty
     */
    protected boolean handleInsertion() {
        ++insertionCountdown;
        if (insertionCountdown >= insertionCountdownTotal) {
            insertionCountdown = 0;
            for (EnumFacing side : EnumFacing.VALUES) {
                Sides blockSide = getSideFromEnumFacing(side);

                if (getConfigurationForSide(blockSide) == SideConfiguration.INPUT) { // If side is configured to input mode
                    BlockPos nearbyPos = pos.offset(side);
                    TileEntity tile = worldObj.getTileEntity(nearbyPos);
                    EnumFacing neighborSide = side.getOpposite();

                    if (tile != null) { // If there is a tile
                        if (tile instanceof IInventory) { // If tile has an inventory
                            boolean continueInsertion = true;

                            if (tile instanceof TileMachineBase) {
                                TileMachineBase machine = (TileMachineBase) tile;
                                if (machine.getConfigurationForSide(blockSide) == getConfigurationForSide(blockSide)) {
                                    continueInsertion = side == EnumFacing.EAST || side == EnumFacing.NORTH || side == EnumFacing.UP;
                                }
                            }
                            if (continueInsertion) {
                                int firstAvailableSlot = InventoryUtils.getFirstAvailableSlot(this, getInputSlots());

                                if (firstAvailableSlot != -1) { // If there is space
                                    IInventory inv = (IInventory) tile;

                                    if (inv instanceof ISidedInventory) {
                                        ISidedInventory sidedInv = (ISidedInventory) inv;

                                        for (int slot : sidedInv.getSlotsForFace(neighborSide)) { // Looping through slots in the neighbor tile, for side
                                            ItemStack stack = sidedInv.getStackInSlot(slot);

                                            if (stack != null && canInsertItem(firstAvailableSlot, stack, side)) { // Condition is OK
                                                if (inventory[firstAvailableSlot] == null) {
                                                    ItemStack copy = stack.copy();
                                                    copy.stackSize = itemsPerTick;
                                                    inventory[firstAvailableSlot] = copy;
                                                    sidedInv.decrStackSize(slot, itemsPerTick);
                                                    if (sidedInv.getStackInSlot(slot) != null) {
                                                        if (sidedInv.getStackInSlot(slot).stackSize == 0) {
                                                            sidedInv.removeStackFromSlot(slot);
                                                        }
                                                    }
                                                    return true;
                                                } else if (inventory[firstAvailableSlot].isItemEqual(stack) && inventory[firstAvailableSlot].stackSize + stack.stackSize <= stack.getMaxStackSize()) {
                                                    inventory[firstAvailableSlot].stackSize = inventory[firstAvailableSlot].stackSize + itemsPerTick;
                                                    sidedInv.decrStackSize(slot, itemsPerTick);
                                                    if (sidedInv.getStackInSlot(slot) != null) {
                                                        if (sidedInv.getStackInSlot(slot).stackSize == 0) {
                                                            sidedInv.removeStackFromSlot(slot);
                                                        }
                                                    }
                                                    return true;
                                                }
                                            }
                                        }
                                    } else {
                                        for (int slot = 0; slot < inv.getSizeInventory(); ++slot) { // Looping through slots in the neighbor tile
                                            ItemStack stack = inv.getStackInSlot(slot);

                                            if (stack != null && canInsertItem(firstAvailableSlot, stack, side)) { // Condition is OK
                                                if (inventory[firstAvailableSlot] == null) {
                                                    ItemStack copy = stack.copy();
                                                    copy.stackSize = itemsPerTick;
                                                    inventory[firstAvailableSlot] = copy;
                                                    inv.decrStackSize(slot, itemsPerTick);
                                                    if (inv.getStackInSlot(slot) != null) {
                                                        if (inv.getStackInSlot(slot).stackSize == 0) {
                                                            inv.removeStackFromSlot(slot);
                                                        }
                                                    }
                                                    return true;
                                                } else if (inventory[firstAvailableSlot].isItemEqual(stack) && inventory[firstAvailableSlot].stackSize + stack.stackSize <= stack.getMaxStackSize()) {
                                                    inventory[firstAvailableSlot].stackSize = inventory[firstAvailableSlot].stackSize + itemsPerTick;
                                                    inv.decrStackSize(slot, itemsPerTick);
                                                    if (inv.getStackInSlot(slot) != null) {
                                                        if (inv.getStackInSlot(slot).stackSize == 0) {
                                                            inv.removeStackFromSlot(slot);
                                                        }
                                                    }
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Attempts to extract items from this inventory to nearby inventories, based on side configuration.
     * @return should mark dirty
     */
    protected boolean handleExtraction() {
        ++extractionCountdown;
        if (extractionCountdown >= extractionCountdownTotal) {
            extractionCountdown = 0;
            for (EnumFacing side : EnumFacing.VALUES) {
                Sides blockSide = getSideFromEnumFacing(side);

                if (getConfigurationForSide(blockSide) == SideConfiguration.OUTPUT) { // If side is configured to output mode
                    BlockPos nearbyPos = pos.offset(side);
                    TileEntity neighborTile = worldObj.getTileEntity(nearbyPos);

                    if (neighborTile != null) { // If there is a tile
                        if (neighborTile instanceof IInventory) { // If tile has an inventory
                            boolean continueInsertion = true;

                            if (neighborTile instanceof TileMachineBase) {
                                TileMachineBase machine = (TileMachineBase) neighborTile;
                                if (machine.getConfigurationForSide(blockSide) == getConfigurationForSide(blockSide)) {
                                    continueInsertion = side == EnumFacing.EAST || side == EnumFacing.NORTH || side == EnumFacing.UP;
                                }
                            }

                            if (continueInsertion) {
                                IInventory neighborInv = (IInventory) neighborTile;
                                int slotToExtract = InventoryUtils.getFirstNonEmptySlot(this, getSlotsForFace(side));

                                if (slotToExtract != -1) { // If can extract anything
                                    ItemStack insertStack = inventory[slotToExtract].copy();
                                    insertStack.stackSize = itemsPerTick;

                                    if (neighborInv instanceof ISidedInventory) { // If nearby inv is sided
                                        ISidedInventory sidedNeighbor = (ISidedInventory) neighborInv;
                                        int[] availableSlots = sidedNeighbor.getSlotsForFace(side.getOpposite());
                                        int slotToInsert = InventoryUtils.getFirstAvailableSlot(sidedNeighbor, availableSlots, insertStack);

                                        if (slotToInsert != -1) { // If can insert
                                            if (sidedNeighbor.canInsertItem(slotToInsert, insertStack, side.getOpposite())) { // If can insert stack
                                                inventory[slotToExtract].stackSize = inventory[slotToExtract].stackSize - itemsPerTick;

                                                if (inventory[slotToExtract].stackSize == 0) {
                                                    removeStackFromSlot(slotToExtract);
                                                }

                                                if (sidedNeighbor.getStackInSlot(slotToInsert) == null) {
                                                    sidedNeighbor.setInventorySlotContents(slotToInsert, insertStack);
                                                } else {
                                                    ItemStack copy = sidedNeighbor.getStackInSlot(slotToInsert).copy();
                                                    copy.stackSize = copy.stackSize + insertStack.stackSize;
                                                    sidedNeighbor.setInventorySlotContents(slotToInsert, copy);
                                                }
                                            }
                                        }
                                    } else { // Inv is not sided, can interact with all slots
                                        int[] availableSlots = InventoryUtils.getSlotArray(neighborInv);
                                        int slotToInsert = InventoryUtils.getFirstAvailableSlot(neighborInv, availableSlots, insertStack);

                                        if (slotToInsert != -1) {
                                            inventory[slotToExtract].stackSize = inventory[slotToExtract].stackSize - itemsPerTick;

                                            if (inventory[slotToExtract].stackSize == 0) {
                                                removeStackFromSlot(slotToExtract);
                                            }

                                            if (neighborInv.getStackInSlot(slotToInsert) == null) {
                                                neighborInv.setInventorySlotContents(slotToInsert, insertStack);
                                            } else {
                                                ItemStack copy = neighborInv.getStackInSlot(slotToInsert).copy();
                                                copy.stackSize = copy.stackSize + insertStack.stackSize;
                                                neighborInv.setInventorySlotContents(slotToInsert, copy);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * When a packet is sent to notify the client of changes in the tile entity, this method is called on the client.
     * What I am doing is reading the contents of the nbt tag to update from server
     * @param net The packet manager instane
     * @param pkt The packet sent
     */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Returns an instance of {@link SPacketUpdateTileEntity}, which is send to the client for client/server
     * synchronization.
     * @return update packet
     */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), writeToNBT(new NBTTagCompound()));
    }

    /**
     * Returns the NBT tag that needs to be sent to the client.
     * @return NBT tag with data
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = super.getUpdateTag();
        writeToNBT(tag);
        return tag;
    }

    /**
     * Attempts to process stuff in the inventory
     * @return should mark dirty
     */
    protected boolean handleProcessing() {
        boolean markDirty = false;
        for (int input : getInputSlots()) {
            if (inventory[input] != null) {
                if (getSmeltingOutput(inventory[input]) != null && canOperate() && shouldOperate()) {
                    if (!isRunning) {
                        double timePercentage = timeModifier / 100;
                        totalProgress = (int) (timePercentage * machineProcessTime);
                        // What this does is calculate the amount of energy to be consumed per tick, by rounding it to a multiple of 10
                        double energyPercentage = (energyModifier / 100) * getEnergyUsage(getSmeltingOutput(inventory[0]));
                        energyConsumptionPerTick = (int) ((energyPercentage / totalProgress) / 10 * 10);
                        if (worldObj.isBlockLoaded(pos)) {
                            if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                                BlockBaseFacingMachine.setWorkingState(true, worldObj, pos);
                            }
                        }
                        setOperationStatus(true);
                        markDirty = true;
                    }
                    ++progress;
                    if (energyConsumptionPerTick >= getEnergyStored()) {
                        stopOperating(true);
                    }
                    energyStorage.modifyEnergyStored(getEnergyStored() - energyConsumptionPerTick);
                    if (progress >= totalProgress) {
                        doOperation();
                        progress = totalProgress = 0;
                        setOperationStatus(false);
                        if (!canOperate()) { // No operation afterwards, so return to default texture
                            if (worldObj.isBlockLoaded(pos)) {
                                if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                                    BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                                }
                            }
                        }
                        markDirty = true;
                    }
                } else {
                    stopOperating(true);
                    if (worldObj.isBlockLoaded(pos)) {
                        if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                            BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                        }
                    }
                }
            } else {
                stopOperating(true);
                if (worldObj.isBlockLoaded(pos)) {
                    if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                        BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                    }
                }
            }
        }

        return markDirty;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void stopOperating(boolean preventOperation) {
        this.preventOperation = preventOperation;
        progress = totalProgress = 0;
        setOperationStatus(false);
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
     * Returns the slot ID array of the energy container slot
     * It needs to return an array in case there is no energy slot, therefore it should
     * return an empty array.
     *
     * @return energy container slot ID array
     */
    @Nonnull
    public abstract int[] getEnergySlots();

    /**
     * Returns the ID(s) of the input slot(s). If none, then return an empty int array.
     *
     * @return input slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getInputSlots();

    /**
     * Returns the ID(s) of the output slot(s). If none, then return an empty int array.
     *
     * @return output slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getOutputSlots();

    /**
     * Booster items are upgrades for the machine.
     * This method should return an array of the slot IDs in which you should
     *
     * @return booster slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getBoosterSlots();

    /**
     * In order to allow the base tile entity to get recipe data without needing
     * to assume the recipe class, the subclass needs to just return a value for that.
     * @param input input item stack
     * @return output for the input given
     */
    public abstract ItemStack getSmeltingOutput(ItemStack input);

    /**
     * This method should return the amount of energy that will get consumed for a recipe with
     * the output as output
     * @param output recipe output
     * @return energy usage for output recipe
     */
    public abstract int getEnergyUsage(ItemStack output);

    /**
     * Should the tile operate right now
     *
     * @return should operate
     */
    @Override
    public boolean shouldOperate() {
        return shouldRun;
    }

    /**
     * Change the field that stores the operation state
     *
     * @param isRunning the new status
     */
    @Override
    public void setOperationStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Returns the value of the {@code isRunning} field
     *
     * @return is it running
     */
    @Override
    public boolean isOperating() {
        return isRunning;
    }

    /**
     * Reads data from NBT and also reads energy storage data
     *
     * @param nbt The tag to be read from
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        energyStorage.modifyEnergyStored(nbt.getInteger("Energy"));
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
     * Writes data to NBT and also writes energy storage data
     *
     * @param nbt The tag to be written to
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setInteger("TotalProgress", totalProgress);
        nbt.setInteger("Energy", getEnergyStored());

        nbt.setInteger("SideConfigFront", getConfigurationForSide(Sides.FRONT).ordinal());
        nbt.setInteger("SideConfigBack", getConfigurationForSide(Sides.BACK).ordinal());
        nbt.setInteger("SideConfigLeft", getConfigurationForSide(Sides.LEFT).ordinal());
        nbt.setInteger("SideConfigRight", getConfigurationForSide(Sides.RIGHT).ordinal());
        nbt.setInteger("SideConfigUp", getConfigurationForSide(Sides.UP).ordinal());
        nbt.setInteger("SideConfigDown", getConfigurationForSide(Sides.DOWN).ordinal());
        return nbt;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return getEnergyStored();
            case 1:
                return progress;
            case 2:
                return totalProgress;
            case 3:
                return energyConsumptionPerTick;
            case 4:
                return getCapacity();
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                energyStorage.modifyEnergyStored(value);
                break;
            case 1:
                progress = value;
                break;
            case 2:
                totalProgress = value;
                break;
            case 3:
                energyConsumptionPerTick = value;
                break;
            case 4:
                energyStorage.setCapacity(value);
        }
    }

    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    public int getCapacity() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public SideConfiguration getConfigurationForSide(Sides side) {
        return sideConfigMap.get(side);
    }

    @Override
    public void setConfigurationForSide(Sides side, SideConfiguration sideConfig) {
        sideConfigMap.put(side, sideConfig);
    }

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
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockBaseFacingMachine.FACING);
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
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockBaseFacingMachine.FACING);
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
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockBaseFacingMachine.FACING);
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

    /**
     * Energy stored.
     *
     * @param from Side energy is stored in
     * @return Amount of energy stored in the TileEntity
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    /**
     * The capacity of the machine.
     *
     * @param from Side max energy is in
     * @return Capacity of the TileEntity
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Checks if energy can be received/extracted from a specific side of the block.
     *
     * @param from Side to be connected
     * @return if it can be connected from that sides
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

}
