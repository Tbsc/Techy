package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IOperator;

import javax.annotation.Nonnull;

/**
 * Basic class for machine tile entities.
 * Adds support for energy consumption and operations.
 * {@link net.minecraft.util.ITickable} is also implemented through
 * {@link IOperator}, because operations must run every tick.
 * <p>
 * Created by tbsc on 4/22/16.
 */
public abstract class TileMachineBase extends TileBase implements IEnergyReceiver, IOperator {

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
    protected int energyConsumptionPerTick = 0;

    /**
     * Amount of energy that needs to be consumed will be divided by this, so it takes
     * in account the boosters that are in the machine.
     * It won't change the amount of energy per tick, but rather the total amount.
     */
    public double energyModifier = 1;

    /**
     * Amount of time for this operation will be divided by this, so it takes in account
     * the boosters that are in the machine.
     * This won't reduce the amount of ticks it progresses every iteration, but rather it
     * reduces the amount of total time it takes to finish the operation.
     */
    public double timeModifier = 1;

    /**
     * Used for {@link #stopOperating(boolean)} to prevent {@link #update()} from operating even though
     * it shouldn't
     */
    protected boolean preventOperation;

    public EnergyStorage energyStorage;
    protected boolean isRunning;
    protected boolean shouldRun = true;

    protected TileMachineBase(int capacity, int maxReceive, int invSize) {
        super(invSize);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    /**
     * Runs every tick, and ATM receives energy from energy container items every tick.
     * Now also takes care of operation sounds.
     */
    @Override
    public void update() {
        // If receiving redstone signal, then prevent machine from operating
        shouldRun = !(worldObj.isBlockIndirectlyGettingPowered(pos) > 0);

        if (getEnergySlots().length >= 1) {
            for (int i = 0; i < getEnergySlots().length; ++i) {
                if (inventory[getEnergySlots()[i]] != null) {
                    receiveEnergy(EnumFacing.NORTH, EnergyHelper.extractEnergyFromContainer(inventory[getEnergySlots()[i]], energyStorage.getMaxReceive(), false), false);
                }
            }
        }

        boolean markDirty = false;

        if (inventory[0] != null) {
            if (getSmeltingOutput(inventory[0]) != null && canOperate() && shouldOperate()) {
                if (!isRunning) {
                    totalProgress = (int) (ConfigData.furnaceDefaultCookTime * timeModifier);
                    // What this does is calculate the amount of energy to be consumed per tick, by rounding it to a multiple of 10
                    energyConsumptionPerTick = (int) Math.round(((((getEnergyUsage(getSmeltingOutput(inventory[0])) * energyModifier) * totalProgress) + 5) / 10) * 10);
                    setOperationStatus(true);
                }
                ++progress;
                if (energyConsumptionPerTick >= getEnergyStored(EnumFacing.DOWN)) {
                    stopOperating(true);
                }
                setEnergyStored(getEnergyStored(EnumFacing.DOWN) - energyConsumptionPerTick);
                if (progress >= totalProgress) {
                    if (!preventOperation) {
                        doOperation();
                        stopOperating(false);
                        markDirty = true;
                    } else {
                        preventOperation = false;
                    }
                }
            } else {
                stopOperating(true);
            }
        } else {
            stopOperating(true);
        }

        if (markDirty) {
            this.markDirty();
        }
    }

    public void spawnXPOrb(int xpAmount, int stackSize) {
        if (xpAmount == 0.0F) {
            stackSize = 0;
        } else if (xpAmount < 1.0F) {
            int j = MathHelper.floor_float((float) stackSize * xpAmount);

            if (j < MathHelper.ceiling_float_int((float) stackSize * xpAmount) && Math.random() < (double) ((float) stackSize * xpAmount - (float) j)) {
                ++j;
            }

            stackSize = j;
        }

        while (stackSize > 0) {
            int k = EntityXPOrb.getXPSplit(stackSize);
            stackSize -= k;
            worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, pos.getX(), pos.getY() + 0.5D, pos.getZ() + 0.5D, k));
        }
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
        energyStorage.readFromNBT(nbt);
    }

    /**
     * Writes data to NBT and also writes energy storage data
     *
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
     * <p>
     * All documentation from now on is made by Team CoFH.
     *
     * @param from       Orientation the energy is received from.
     * @param maxReceive Maximum amount of energy to receive.
     * @param simulate   If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        worldObj.markBlockForUpdate(pos);
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    public void setEnergyStored(int energy) {
        energyStorage.setEnergyStored(energy);
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
