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

package tbsc.techy.block.pipe;

import cofh.api.energy.IEnergyConnection;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.tile.pipe.TilePipeEnergy;

import java.util.List;

/**
 * Block for the energy pipe
 *
 * Created by tbsc on 5/9/16.
 */
public class BlockPipeEnergy extends BlockPipeBase {

    protected int capacity;
    protected int maxTransfer;

    public BlockPipeEnergy(String registryName, int capacity, int maxTransfer) {
        super(registryName, 0, BlockPipeEnergy.class);
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
    }

    @Override
    public boolean canConnectOnSide(IBlockAccess world, BlockPos thisBlock, EnumFacing side) {
        return canConnectWithBlock(world, thisBlock.offset(side));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("Capacity: " + capacity + " RF");
        tooltip.add("Max transfer: " + maxTransfer + " RF/t");
    }

    @Override
    public boolean canConnectWithBlock(IBlockAccess world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        TileEntity tile = world.getTileEntity(blockPos);
        return (tile != null && tile instanceof IEnergyConnection) || block instanceof BlockPipeEnergy; // If the block can connect to energy stuff, then we can connect to it
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeEnergy(capacity, maxTransfer);
    }

}
