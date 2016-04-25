package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import tbsc.techy.api.IOperator;

import javax.annotation.Nonnull;

/**
 * Basic class for machine tile entities.
 * Adds support for energy consumption and operations.
 * {@link net.minecraft.util.ITickable} is also implemented through
 * {@link IOperator}, because operations must run every tick.
 *
 * Created by tbsc on 4/22/16.
 */
public abstract class TileMachineBase extends TileBase implements IEnergyReceiver, IOperator {

    public EnergyStorage energyStorage;
    protected boolean isRunning;
    protected boolean shouldRun;

    protected TileMachineBase(int capacity, int maxReceive, int invSize) {
        super(invSize);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    /**
     * Runs every tick, and ATM receives energy from energy container items every tick.
     */
    @Override
    public void update() {
        // If receiving redstone signal, then prevent machine from operating
        shouldRun = worldObj.isBlockIndirectlyGettingPowered(pos) <= 0;

        if (getEnergySlot().length >= 1) {
            for (int i : getEnergySlot()) {
                if (inventory[getEnergySlot()[i]] != null) {
                    receiveEnergy(EnumFacing.NORTH, EnergyHelper.extractEnergyFromContainer(inventory[getEnergySlot()[i]], energyStorage.getMaxReceive(), false), false);
                }
            }
        }
    }

    /**
     * Returns the slot ID array of the energy container slot
     * It needs to return an array in case there is no energy slot, therefore it should
     * return an empty array.
     * @return energy container slot ID array
     */
    @Nonnull
    public abstract int[] getEnergySlot();

    /**
     * Returns the ID(s) of the input slot(s). If none, then return an empty int array.
     * @return input slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getInputSlot();

    /**
     * Returns the ID(s) of the output slot(s). If none, then return an empty int array.
     * @return output slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getOutputSlot();

    /**
     * Booster items are upgrades for the machine.
     * This method should return an array of the slot IDs in which you should
     * @return booster slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    public abstract int[] getBoosterSlots();

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
     * Returns the value of the {@code isRunning} field
     * @return is it running
     */
    @Override
    public boolean isOperating() {
        return isRunning;
    }

    /**
     * Reads data from NBT and also reads energy storage data
     * @param nbt The tag to be read from
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
    }

    /**
     * Writes data to NBT and also writes energy storage data
     * @param nbt The tag to be written to
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
    }

    /**
     * Since receiving energy will be done exactly the same on all machines,
     * I'm doing it on the base class so it'll apply to all machines.
     *
     * All documentation from now on is made by Team CoFH.
     *
     * @param from
     *            Orientation the energy is received from.
     * @param maxReceive
     *            Maximum amount of energy to receive.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    public void setEnergyStored(int energy) {
        energyStorage.setEnergyStored(energy);
    }

    /**
     * Energy stored.
     * @param from Side energy is stored in
     * @return Amount of energy stored in the TileEntity
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    /**
     * The capacity of the machine.
     * @param from Side max energy is in
     * @return Capacity of the TileEntity
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Checks if energy can be received/extracted from a specific side of the block.
     * @param from Side to be connected
     * @return if it can be connected from that sides
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

}
