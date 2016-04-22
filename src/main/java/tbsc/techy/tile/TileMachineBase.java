package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
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
     * Energy stored in a specific side (sides can be ignored).
     * @param from Direction to check for energy, added for compatibility
     * @return Amount of energy stored in the TileEntity/that specific side
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    /**
     * The capacity of the machine.
     * @param from Direction to check for capacity, added for compatibility
     * @return Capacity of the TileEntity or of that side
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Can be used to configure sides, as it checks if you can connect energy from specific sides.
     * @param from Direction from which energy *can* come
     * @return Whether energy is allowed to come from that direction.
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true; // TODO Reconfigurable sides
    }

}
