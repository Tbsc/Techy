package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.util.EnumFacing;

/**
 * Created by tbsc on 3/27/16.
 */
public class TileMachineBase extends TileBase implements IEnergyReceiver {

    protected EnergyStorage energyStorage;

    public TileMachineBase(int capacity, int maxReceive) {
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

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

}
