package tbsc.techy.item;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

/**
 * Battery base, holds amount of RF based on data
 *
 * Created by tbsc on 5/5/16.
 */
public class ItemBatteryBase extends ItemBase implements IEnergyContainerItem {

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ItemBatteryBase(String unlocalized, int capacity, int maxReceive, int maxExtract) {
        super(unlocalized);
        this.capacity = capacity;
        this.maxExtract = maxExtract;
        this.maxReceive = maxReceive;
        setMaxStackSize(1);
        setMaxDamage(capacity);
    }

    public ItemBatteryBase setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ItemBatteryBase setMaxTransfer(int maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public ItemBatteryBase setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public ItemBatteryBase setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(String.format("Energy: %s / %s RF", getEnergyStored(stack), capacity));
        tooltip.add(String.format("Extract / Receive: %s RF / %s RF", maxExtract, maxReceive));
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        ItemStack energyFull = new ItemStack(itemIn, 1, 0);
        receiveEnergy(energyFull, capacity, false);
        subItems.add(energyFull);
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

        if (!container.hasTagCompound()) {
            container.setTagCompound(new NBTTagCompound());
        }
        int energy = container.getTagCompound().getInteger("Energy");
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            energy += energyReceived;
            container.getTagCompound().setInteger("Energy", energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

        if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
            return 0;
        }
        int energy = container.getTagCompound().getInteger("Energy");
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            energy -= energyExtracted;
            container.getTagCompound().setInteger("Energy", energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack container) {
        if (container.getTagCompound() == null || !container.getTagCompound().hasKey("Energy")) {
            return 0;
        }
        return container.getTagCompound().getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) {
        return capacity;
    }

}
