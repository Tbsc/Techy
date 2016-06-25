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

package tbsc.techy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.block.BlockBaseFacingMachine;

/**
 * Basic TileEntity class, adds support for inventories and basic data saving.
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class TileBase extends TileEntity implements ISidedInventory {

    public ItemStack[] inventory;
    public int inventorySize;

    @CapabilityInject(CapabilityItemHandler.class)
    public static final Capability<IItemHandler> CAPABILITY_ITEM_HANDLER = null;

    /**
     *
     * @param invSize STARTING FROM _1_!! E.g. inventory has slot 0 for input and 1 for output, invSize would be 2!
     */
    public TileBase(int invSize) {
        this.inventory = new ItemStack[invSize];
        this.inventorySize = invSize;
    }

    /**
     * Writes data to NBT tags, for use on next world/chunk load.
     * @param nbt The tag to be written to
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
        return nbt;
    }

    /**
     * Reads data from NBT tags, for use when world/chunk is loaded.
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
    }

    /**
     * Returns the side configuration of the side given.
     * @param side to check
     * @return side config of the side given
     */
    public abstract SideConfiguration getConfigurationForSide(Sides side);

    /**
     * Sets the side configuration for the side given
     * @param side side to change
     * @param sideConfig what to change to
     */
    public abstract void setConfigurationForSide(Sides side, SideConfiguration sideConfig);

    /**
     * Returns the slots that, based on the configuration, can be accessed.
     * @param sideConfig config to check
     * @return slots accessible
     */
    public abstract int[] getSlotsForConfiguration(SideConfiguration sideConfig);

    /**
     * Returns a {@link Sides} value corresponding to the {@link EnumFacing} given, correct
     * for this tile entity.
     * @param side to check
     * @return Side for the enum facing
     */
    public Sides getSideFromEnumFacing(EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockBaseFacingMachine.FACING);
        if (frontOfBlock == side) { // FRONT
            return Sides.FRONT;
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            return Sides.LEFT;
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            return Sides.BACK;
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            return Sides.RIGHT;
        }
        if (side == EnumFacing.UP) { // UP
            return Sides.UP;
        }
        if (side == EnumFacing.DOWN) { // DOWN
            return Sides.DOWN;
        }
        return Sides.UNKNOWN; // seriously, wtf, it can't return unknown because I just checked every type of EnumFacing
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) new SidedInvWrapper(this, facing);
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    /**
     * When TileEntity update packet {@link SPacketUpdateTileEntity} is received, read the data
     * from the NBT tag inside of it, to sync with the server/client.
     * @param net The packet manager instane
     * @param pkt The packet sent
     */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * When TileEntity update packet is needed to get sent, this method is executed.
     * It creates a {@link SPacketUpdateTileEntity} packet instance and returns it to
     * be sent.
     * @return instance of {@link SPacketUpdateTileEntity} to be sent
     */
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    /**
     * @return Inventory size of the block
     */
    @Override
    public int getSizeInventory() {
        return inventorySize;
    }

    /**
     * Checks inside the inventory array for the itemstack in the slot specified, and
     * return it. If index is invalid, return null.
     * @param index slot to be checked
     * @return stack in said slot, if index is invalid then returns null
     */
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= this.getSizeInventory())
            return null;
        return this.inventory[index];
    }

    /**
     * Decreases "count" from ItemStack in slot "index", then returns the new ItemStack.
     * @param index slot
     * @param count amount
     * @return new itemstack after decrease
     */
    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.getStackInSlot(index) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(index).stackSize <= count) {
                itemstack = this.getStackInSlot(index);
                this.setInventorySlotContents(index, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(index).splitStack(count);

                if (this.getStackInSlot(index).stackSize <= 0) {
                    this.setInventorySlotContents(index, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(index, this.getStackInSlot(index));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * Clears slot "index".
     * @param index The slot to be cleared
     * @return the ItemStack in the slot before it got cleared.
     */
    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack original = inventory[index].copy();
        inventory[index] = null;
        return original;
    }

    /**
     * Sets the ItemStack in the slot given.
     * @param index The slot to change
     * @param stack The stack to change the slot's ItemStack to.
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= this.getSizeInventory())
            return;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();

        if (stack != null && stack.stackSize == 0)
            stack = null;

        this.inventory[index] = stack;
        this.markDirty();
    }

    /**
     * Return the inventory's slots stack amount limit.
     * @return max items per stack
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * Checks if the player can use the tile entity.
     * @param player The player that's checked
     * @return if the player is in range of 8 blocks from the block.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(pos) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }

    /**
     * Not used
     */
    @Override
    public void openInventory(EntityPlayer player) {
        // NO-OP
    }

    /**
     * Not used
     */
    @Override
    public void closeInventory(EntityPlayer player) {
        // NO-OP
    }

    /**
     * Clears the inventory completely.
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++)
            this.setInventorySlotContents(i, null);
    }

    /**
     * Returns if the tile has a custom name, which it doesn't. Therefore it always returns false.
     * @return if the tile has a custom name (it's always false!)
     */
    @Override
    public boolean hasCustomName() {
        return false;
    }

}
