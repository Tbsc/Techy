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

package tbsc.techy.machine.generator;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.tile.TileMachineBase;

/**
 * Base tile entity for generators
 *
 * Created by tbsc on 5/20/16.
 */
public abstract class TileGeneratorBase extends TileMachineBase implements IEnergyProvider {

    protected TileGeneratorBase(int capacity, int maxReceive, int invSize, int cookTime, int itemsPerTick) {
        super(capacity, maxReceive, invSize, cookTime, itemsPerTick);
    }

    @Override
    public void update() {
        if (handleProcessing() || handleExtraction() || handleRedstone() || handleBoosters()) {
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
                        totalProgress = getBurnTimeFromItem(inventory[0]);

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
                        if (!canOperate()) {
                            if (worldObj.isBlockLoaded(pos)) {
                                if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                                    BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                                }
                            }
                        }
                    }
                } else {
                    stopOperating(true);
                    if (!canOperate()) {
                        if (worldObj.isBlockLoaded(pos)) {
                            if (BlockBaseFacingMachine.isCorrectBlock(worldObj, pos, BlockBaseFacingMachine.class)) {
                                BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                            }
                        }
                    }
                }
            }
        }

        if (shouldOperate()) {
            if (isOperating()) {
                progress = (int) (progress * timeModifier);

                // Not enough room for added energy
                if (energyConsumptionPerTick + getEnergyStored() <= getCapacity()) {
                    energyStorage.setEnergyStored(getEnergyStored() + energyConsumptionPerTick);
                }

                if (progress >= totalProgress) {
                    doOperation();
                    progress = totalProgress = 0;
                    setOperationStatus(false);
                    if (!canOperate()) {
                        BlockBaseFacingMachine.setWorkingState(false, worldObj, pos);
                    }
                    markDirty = true;
                }
            }
        }

        return markDirty;
    }

    /**
     * Attempts to insert energy to nearby {@link cofh.api.energy.IEnergyReceiver}s.
     * @return Whether it should mark the block as dirty
     */
    protected boolean handleExtraction() {
        boolean markDirty = false;

        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos offset = pos.offset(side);
            TileEntity tile = worldObj.getTileEntity(offset);
            if (tile != null) {
                if (tile instanceof IEnergyReceiver) {
                    IEnergyReceiver receiver = (IEnergyReceiver) tile;
                    if (receiver.getEnergyStored(side.getOpposite()) + extractEnergy(side, energyStorage.getMaxExtract(), true) <= receiver.getMaxEnergyStored(side.getOpposite())) {
                        receiver.receiveEnergy(side.getOpposite(), extractEnergy(side.getOpposite(), energyStorage.getMaxExtract(), false), false);
                        markDirty = true;
                    }
                }
            }
        }

        return markDirty;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        this.markDirty();
        worldObj.markBlockRangeForRenderUpdate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
//        Techy.network.sendToAll(new CPacketEnergyChanged(pos, getEnergyStored()));
        return energyStorage.extractEnergy(maxExtract, simulate);
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
    public boolean canOperate() {
        if (inventory[0] == null) {
            return false;
        } else {
            if (!canGenerateFromItem(inventory[0])) {
                return false;
            }
            return getEnergyUsage(inventory[0]) + getEnergyStored() < getCapacity();
        }
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

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

}
