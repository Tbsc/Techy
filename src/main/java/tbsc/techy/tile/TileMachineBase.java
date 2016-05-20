package tbsc.techy.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.MutablePair;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.IOperator;
import tbsc.techy.block.BlockBaseFacingMachine;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

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
     * Used for {@link #stopOperating(boolean)} to prevent {@link #update()} from operating even though
     * it shouldn't
     */
    protected boolean preventOperation = false;

    public EnergyStorage energyStorage;
    protected boolean isRunning;
    protected boolean shouldRun = true;

    /**
     * In order to keep data of previous boosters in order for me to undo their modifiers,
     * I need to save an instance of the booster, so when it is removed (not that item anymore),
     * I can also use the {@link ItemStack} instance I saved to check what are the modifiers
     * and revert those.
     *
     * The key is an integer, more specifically the booster slot ID (based on {@link #getBoosterSlots()}.
     * The value is a {@link MutablePair}, and it has a boolean on the left letting me know
     * if this booster has already been appplied, to prevent applying boosters a ton of times.
     * On the right it has the copy of the booster's {@link ItemStack} (and not {@link IBoosterItem}
     * or {@link Item} because I need to know the metadata in order to correctly undo the
     * booster).
     */
    Map<Integer, MutablePair<Boolean, ItemStack>> boosterApplied = new HashMap<>();

    protected TileMachineBase(int capacity, int maxReceive, int invSize, int cookTime) {
        super(invSize);
        this.machineProcessTime = cookTime;
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    @Override
    public void update() {
        handleRedstone();
        handleEnergyItems();
        handleBoosters();

        boolean markDirty;

        markDirty = handleProcessing();

        if (markDirty) {
            this.markDirty();
        }
    }

    protected boolean handleBoosters() {
        int energyModifierSet = 100, timeModifierSet = 100, experienceModifierSet = 100, additionalItemModifierSet = 0;
        if (getBoosterSlots().length >= 1) {
            for (int i = 0; i < getBoosterSlots().length; ++i) {
                if (inventory[getBoosterSlots()[i]] != null) {
                    // No need to check if item is a booster, because only booster items area allowed there
                    IBoosterItem booster = (IBoosterItem) inventory[getBoosterSlots()[i]].getItem();
                    int tier = inventory[getBoosterSlots()[i]].getMetadata();
                    energyModifierSet =+ booster.getEnergyModifier(tier);
                    timeModifierSet = booster.getTimeModifier(tier);
                    experienceModifierSet =+ booster.getExperienceModifier(tier);
                    additionalItemModifierSet =+ booster.getAdditionalItemModifier(tier);
                }
            }
        }
        energyModifier = energyModifierSet;
        timeModifier = timeModifierSet;
        experienceModifier = experienceModifierSet;
        additionalItemModifier = additionalItemModifierSet;
        return false;
    }

    protected boolean handleRedstone() {
        // If receiving redstone signal, then prevent machine from operating
        shouldRun = !(worldObj.isBlockIndirectlyGettingPowered(pos) > 0);
        return false;
    }

    protected boolean handleEnergyItems() {
        if (getEnergySlots().length >= 1) {
            for (int i = 0; i < getEnergySlots().length; ++i) {
                if (inventory[getEnergySlots()[i]] != null) {
                    receiveEnergy(EnumFacing.NORTH, EnergyHelper.extractEnergyFromContainer(inventory[getEnergySlots()[i]], energyStorage.getMaxReceive(), false), false);
                }
            }
        }
        return false;
    }

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
                        if (worldObj.getBlockState(pos).getBlock() == null) {
                            BlockBaseFacingMachine.setState(true, worldObj, pos);
                        }
                        setOperationStatus(true);
                    }
                    ++progress;
                    if (energyConsumptionPerTick >= getEnergyStored(EnumFacing.DOWN)) {
                        stopOperating(true);
                    }
                    setEnergyStored(getEnergyStored(EnumFacing.DOWN) - energyConsumptionPerTick);
                    if (progress >= totalProgress) {
                        doOperation();
                        if (worldObj.getBlockState(pos).getBlock() == null) {
                            BlockBaseFacingMachine.setState(false, worldObj, pos);
                        }
                        progress = totalProgress = 0;
                        setOperationStatus(false);
                        markDirty = true;
                    }
                } else {
                    stopOperating(true);
                    if (BlockBaseFacingMachine.getState(worldObj, pos)) {
                        BlockBaseFacingMachine.setState(false, worldObj, pos);
                    }
                }
            } else {
                stopOperating(true);
                if (BlockBaseFacingMachine.getState(worldObj, pos)) {
                    BlockBaseFacingMachine.setState(false, worldObj, pos);
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
        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }
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
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);
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
