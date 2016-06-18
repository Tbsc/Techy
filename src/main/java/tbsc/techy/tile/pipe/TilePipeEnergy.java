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

package tbsc.techy.tile.pipe;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import tbsc.techy.block.pipe.BlockPipeBase;
import tbsc.techy.init.BlockInit;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic tile class for energy pipes.
 * Has basic functions for (example) transferring the current object to the next pipe.
 *
 * Created by tbsc on 5/29/16.
 */
public class TilePipeEnergy extends TileEntity implements ITickable, IEnergyReceiver, IEnergyProvider {

    public EnergyStorage energyStorage = new EnergyStorage(960, 160);

    /**
     * Minecraft requires me to have this constructor here
     */
    public TilePipeEnergy() {}

    public TilePipeEnergy(int capacity, int maxTransfer) {
        this.energyStorage = new EnergyStorage(capacity, maxTransfer);
    }

    /**
     * Gets called every tick, and in this case, attempts to transfer objects
     * to nearby destinations
     */
    @Override
    public void update() {
        List<EnumFacing> transferableSides = getTransferableSides();
        if (!transferableSides.isEmpty()) {
            int forEach = energyStorage.getEnergyStored() / transferableSides.size();
            for (EnumFacing side : transferableSides) {
                BlockPos neighbor = pos.offset(side);
                TileEntity tile = worldObj.getTileEntity(neighbor);
                // Double checking
                if (tile != null) {
                    if (tile instanceof IEnergyReceiver) {
                        IEnergyReceiver receiver = (IEnergyReceiver) tile;
                        receiver.receiveEnergy(side.getOpposite(), extractEnergy(side, forEach, false), false);
                    }
                }
            }
        }

        IBlockState state = worldObj.getBlockState(pos);

        if (state.getValue(BlockPipeBase.NORTH) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.NORTH)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.NORTH, !state.getValue(BlockPipeBase.NORTH)));
        }
        if (state.getValue(BlockPipeBase.SOUTH) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.SOUTH)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.SOUTH, !state.getValue(BlockPipeBase.SOUTH)));
        }
        if (state.getValue(BlockPipeBase.WEST) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.WEST)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.WEST, !state.getValue(BlockPipeBase.WEST)));
        }
        if (state.getValue(BlockPipeBase.EAST) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.EAST)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.EAST, !state.getValue(BlockPipeBase.EAST)));
        }
        if (state.getValue(BlockPipeBase.UP) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.UP)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.UP, !state.getValue(BlockPipeBase.UP)));
        }
        if (state.getValue(BlockPipeBase.DOWN) != BlockInit.blockPipeEnergyBasic.canConnectOnSide(worldObj, pos, EnumFacing.DOWN)) {
            worldObj.setBlockState(pos, state.withProperty(BlockPipeBase.DOWN, !state.getValue(BlockPipeBase.DOWN)));
        }
    }

    /**
     * Returns a list of sides of this block, to which any object given can be transferred
     * to.
     * @return sides that stuff can be transferred to
     */
    public List<EnumFacing> getTransferableSides() {
        List<EnumFacing> sides = new ArrayList<>();
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos neighbor = pos.offset(side);
            TileEntity tile = worldObj.getTileEntity(neighbor);
            if (tile != null) {
                if (tile instanceof IEnergyReceiver) {
                    sides.add(side);
                }
            }
        }

        if (sides.size() != 0) {
            int forEach = energyStorage.getEnergyStored() / sides.size();

            for (EnumFacing side : EnumFacing.VALUES) {
                if (sides.contains(side)) {
                    BlockPos neighbor = pos.offset(side);
                    TileEntity tile = worldObj.getTileEntity(neighbor);
                    if (tile != null && tile instanceof IEnergyReceiver) {
                        IEnergyReceiver receiver = (IEnergyReceiver) tile;
                        if (receiver.getEnergyStored(side.getOpposite()) + forEach > receiver.getMaxEnergyStored(side.getOpposite())) { // can't hold more energy
                            sides.remove(side);
                        }
                    }
                }
            }
        }
        return sides;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        energyStorage.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        energyStorage.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(super.getUpdateTag());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
    }

    /**
     * Returns the energy currently stored in the tile
     * @param from side energy stored in
     * @return energy stored currently
     */
    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    /**
     * Returns the capacity of the tile
     * @param from capacity of side
     * @return max energy storage
     */
    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Returns the energy currently stored in the tile, without side
     * @return energy stored currently
     */
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    /**
     * Returns the capacity of the tile, without side
     * @return max energy storage
     */
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    /**
     * Energy pipes do NOT support blocking transfer based on wrench toggle. Therefore returns true.
     * However redstone signal does change this, and if this block is powered on any signal, it
     * will NOT transfer anything. This is how all pipes behave.
     * @param from side to check if can connect to
     * @return can connect
     */
    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return worldObj.isBlockIndirectlyGettingPowered(pos) >= 1; // is powered by at least a signal of power 1
    }

    /**
     * Extract energy from this tile entity.
     * @param from
     *            Orientation the energy is extracted from.
     * @param maxExtract
     *            Maximum amount of energy to extract.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return amount of energy extracted
     */
    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    /**
     * Received the amount of energy given
     * @param from
     *            Orientation the energy is received from.
     * @param maxReceive
     *            Maximum amount of energy to receive.
     * @param simulate
     *            If TRUE, the charge will only be simulated.
     * @return amount of energy received
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }
}
