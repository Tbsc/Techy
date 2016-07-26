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

package tbsc.techy.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Base class for tile entities.
 * Adds support for inventories and NBT data reading/saving.
 *
 * Created by tbsc on 13/07/2016.
 */
public class TileTechyBase extends TileEntity {

    /**
     * The inventory of the tile.
     */
    public ItemStackHandler itemHandler;

    public TileTechyBase(int invSize) {
        this.itemHandler = new ItemStackHandler(invSize);
    }

    /**
     * Deserializes data from NBT tags and loads it to the tile.
     * @param compound NBT data
     */
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        itemHandler.deserializeNBT(compound.getCompoundTag("Items"));
    }

    /**
     * Serializes data to NBT tags.
     * @param compound NBT data
     * @return NBT data + custom data
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Items", itemHandler.serializeNBT());
        return super.writeToNBT(compound);
    }

    /**
     * Returns the tag that is sent on update packets
     * @return Update packet tag
     */
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    /**
     * Returns the packet that is sent on tile synchronisation
     * @return tile update packet
     */
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), writeToNBT(new NBTTagCompound()));
    }

    /**
     * What to do when update packet is received
     * @param net Network manager, used to get context
     * @param pkt The packet itself, containing the NBT data
     */
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    /**
     * Checks if tile has a capability
     * @param capability The capability to see if the tile has
     * @param facing The side to check the capability for
     * @return If the tile has that capability
     */
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }

    /**
     * Returns the value of the capability
     * @param capability The capability to return the value for
     * @param facing The side for the capability
     * @param <T> The type I should return
     * @return Value for the capability, or null if invalid capability or side
     */
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) itemHandler;
        return super.getCapability(capability, facing);
    }

    /**
     * Should Minecraft recreate the tile entity of the block when it changes, including changes
     * in properties.
     * To allow for changing the side the block is facing or the working state, WITHOUT the tile
     * resetting.
     * @param world The world
     * @param pos Position of the tile
     * @param oldState Previous state
     * @param newState New state
     * @return Should replace the current (previous) state with the new state
     */
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

}
