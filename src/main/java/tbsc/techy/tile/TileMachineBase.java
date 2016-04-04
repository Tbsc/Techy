package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumFacing;
import tbsc.techy.api.IOperator;

/**
 * The base TileEntity class for machines, adds support for energy receiving (if
 * support for extracting energy is needed, just implement {@link IEnergyReceiver}.
 * Also adds support for operations, and is abstract as machines need to implement
 * it themselves (mostly, {@code IOperator.canOperate()} is provided by
 * {@link TileMachineBase} already in the form of a boolean field called {@code isRunning}).
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class TileMachineBase extends TileBase implements IEnergyReceiver, IOperator {

    protected EnergyStorage energyStorage;
    protected boolean isRunning;
    protected boolean shouldRun;
    protected int operationTotalTime = 0;
    protected int operationTimeLeft = 0;

    protected TileMachineBase(int capacity, int maxReceive, int invSize) {
        super(invSize);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    @Override
    public void update() {
        if (canOperate() && shouldOperate()) { // It needs to be able to run and should run to work
            doOperation();
            isRunning = true;
        } else {
            isRunning = false;
        }
    }

    // TODO ISidedInventory

    // Getter methods
    public EnergyStorage getEnergyStorage() { return energyStorage; }
    public int getEnergyStored() { return energyStorage.getEnergyStored(); }
    public int getCapacity() { return energyStorage.getMaxEnergyStored(); }
    public int getMaxReceive() { return energyStorage.getMaxReceive(); }
    // Setter methods
    public void setEnergyStored(int energy) { energyStorage.setEnergyStored(energy); }
    public void setMaxReceive(int maxReceive) { energyStorage.setMaxReceive(maxReceive); }
    public void setCapacity(int capacity) { energyStorage.setCapacity(capacity); }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true; // TODO Change to be reconfigurable
    }

    @Override
    public boolean shouldOperate() {
        return true;
    } // TODO Add redstone control

    @Override
    public void setOperationStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public boolean isOperating() {
        return isRunning;
    }

}
