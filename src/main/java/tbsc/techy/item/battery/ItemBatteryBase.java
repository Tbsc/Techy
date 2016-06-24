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

package tbsc.techy.item.battery;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tbsc.techy.item.ItemBase;

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

    protected ItemBatteryBase(String unlocalized, int capacity, int maxReceive, int maxExtract) {
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
        if (!energyFull.hasTagCompound()) {
            energyFull.setTagCompound(new NBTTagCompound());
        }
        energyFull.getTagCompound().setInteger("Energy", capacity);
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
