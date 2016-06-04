package tbsc.techy.machine.powercell;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;

/**
 * Generic class for power cell tile entities.
 *
 * Created by tbsc on 6/3/16.
 */
public abstract class TilePowerCellBase extends TileMachineBase implements IEnergyReceiver, IEnergyProvider {

    public String registryName;

    public TilePowerCellBase(int capacity, int maxTransfer, int invSize, String registryName) {
        super(capacity, maxTransfer, invSize, 0, 1);
        this.registryName = registryName;

        setConfigurationForSide(Sides.UP, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.DOWN, SideConfiguration.OUTPUT);
        setConfigurationForSide(Sides.FRONT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.BACK, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.LEFT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.RIGHT, SideConfiguration.INPUT);
    }

    @Override
    public void update() {
        boolean markDirty = false;

        if (canExtractEnergyFromContainer()) {
            markDirty = extractEnergyFromContainer();
        }
        if (canInsertEnergyToContainer()) {
            markDirty = insertEnergyToContainer();
        }

        if (markDirty) this.markDirty();
    }

    /**
     * Used by GUI handler to handle GUIs
     * @return location of GUI texture
     */
    public abstract ResourceLocation getGUITexture();

    protected boolean extractEnergyFromContainer() {
        if (getInputEnergySlots().length >= 1) {
            for (int slot : getInputEnergySlots()) {
                IEnergyContainerItem energyContainer = (IEnergyContainerItem) inventory[slot].getItem();
                int extractedEnergy = receiveEnergy(EnumFacing.DOWN, energyContainer.extractEnergy(inventory[slot], Math.min(getCapacity() - getEnergyStored(), energyStorage.getMaxExtract()), false), false);
                if (extractedEnergy >= 1) return true;
            }
        }
        return false;
    }

    protected boolean insertEnergyToContainer() {
        if (getOutputEnergySlots().length >= 1) {
            for (int slot : getOutputEnergySlots()) {
                if (inventory[slot] != null) {
                    IEnergyContainerItem energyContainer = (IEnergyContainerItem) inventory[slot].getItem();
                    int extractedEnergy = extractEnergy(EnumFacing.DOWN, energyContainer.receiveEnergy(inventory[slot], Math.min(getCapacity() - getEnergyStored(), energyStorage.getMaxReceive()), false), false);
                    if (extractedEnergy >= 1) return true;
                }
            }
        }
        return false;
    }

    protected boolean canExtractEnergyFromContainer() {
        if (getInputEnergySlots().length >= 1) {
            for (int slot : getInputEnergySlots()) {
                if (inventory[slot] != null) {
                    if (inventory[slot].getItem() instanceof IEnergyContainerItem) {
                        IEnergyContainerItem energy = (IEnergyContainerItem) inventory[slot].getItem();

                        if (energy.getEnergyStored(inventory[slot]) > 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected boolean canInsertEnergyToContainer() {
        if (getOutputEnergySlots().length >= 1) {
            for (int slot : getOutputEnergySlots()) {
                if (inventory[slot] != null) {
                    if (inventory[slot].getItem() instanceof IEnergyContainerItem) {
                        IEnergyContainerItem energy = (IEnergyContainerItem) inventory[slot].getItem();

                        if (energy.getEnergyStored(inventory[slot]) < energy.getMaxEnergyStored(inventory[slot])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Used by the generic tile to extract/receive energy from energy container items, without
     * needing to search for the slots.
     * @return array of input energy slots
     */
    public abstract int[] getInputEnergySlots();

    /**
     * Used by the generic tile to extract/receive energy from energy container items, without
     * needing to search for the slots.
     * @return array of output energy slots
     */
    public abstract int[] getOutputEnergySlots();

    /**
     * Not used
     */
    @Override
    public void doOperation() { /* NO-OP */ }

    /**
     * Not usable in this case, so returns false
     * @return false
     */
    @Override
    public boolean canOperate() { return false; /* NO-OP */ }

    @Nonnull
    @Override
    public int[] getEnergySlots() {
        return new int[] {
                0, 1
        };
    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[0];
    }

    /**
     * This machine does no processing; therefore returns null
     * @param input input item stack
     * @return null
     */
    @Override
    public ItemStack getSmeltingOutput(ItemStack input) { return null; /* NO-OP */ }

    /**
     * This machine only holds energy; doesn't create ore use energy.
     * @param output recipe output
     * @return 0
     */
    @Override
    public int getEnergyUsage(ItemStack output) { return 0; /* NO-OP */ }

    /**
     * Checks if the item given is valid for the slot given.
     * @param index the slot to check if the item is valid
     * @param stack the item to check
     * @return if the item can be in the slot given
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof IEnergyContainerItem;
    }

    @Override
    public String getName() {
        return I18n.translateToLocal("tile.Techy:" + registryName + ".name");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

}
