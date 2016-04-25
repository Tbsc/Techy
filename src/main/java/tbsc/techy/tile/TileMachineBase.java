package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import tbsc.techy.api.IOperator;

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

    protected TileMachineBase(int capacity, int maxReceive, int invSize) {
        super(invSize);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    /**
     * Runs every tick, and ATM receives energy from energy container items every tick.
     */
    @Override
    public void update() {
        if (inventory[2] != null) {
            receiveEnergy(EnumFacing.NORTH, EnergyHelper.extractEnergyFromContainer(inventory[2], energyStorage.getMaxReceive(), false), false);
        }
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
