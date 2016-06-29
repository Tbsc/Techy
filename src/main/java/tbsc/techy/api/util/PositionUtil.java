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

package tbsc.techy.api.util;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Utility class for checking position
 *
 * Created by tbsc on 5/14/2016.
 */
public class PositionUtil {

    /**
     * Checks if the block's neighbor positions equal to the neighbor given
     * @param thisBlock the block to check neighbors'
     * @param neighbor the neighbor
     * @return if they are adjacent
     */
    public static boolean isNeighbor(BlockPos thisBlock, BlockPos neighbor) {
        return thisBlock.north() == neighbor || thisBlock.south() == neighbor || thisBlock.east() == neighbor || thisBlock.west() == neighbor || thisBlock.up() == neighbor || thisBlock.down() == neighbor;
    }

    /**
     * This method will loop through the neighbors of the given location, and
     * if they match the given class, then it returns a list with applicable
     * neighbors
     * @param applicableNeighborTypes Type(s) of applicable class
     */
    public static BlockPos[] getApplicableNeighbors(IBlockAccess world, BlockPos thisBlock, Class<?>... applicableNeighborTypes) {
        BlockPos[] applicableNeighbors = new BlockPos[]{};
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos neighbor = thisBlock.offset(side);
            Block block = world.getBlockState(neighbor).getBlock();
            for (Class<?> clazz : applicableNeighborTypes) {
                if (block.getClass().isInstance(clazz)) {
                    ArrayUtils.add(applicableNeighbors, neighbor);
                }
            }
        }
        return applicableNeighbors;
    }

    /**
     * This method will loop through the neighbors of the given location, and
     * if the tile entity (if there is one) match the given class, then it returns a list with applicable
     * neighbors
     * @param applicableNeighborTypes Type(s) of applicable class of TILE!
     */
    public static BlockPos[] getTileApplicableNeighbors(IBlockAccess world, BlockPos thisBlock, Class<?>... applicableNeighborTypes) {
        BlockPos[] applicableNeighbors = new BlockPos[]{};
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos neighbor = thisBlock.offset(side);
            TileEntity block = world.getTileEntity(neighbor);
            for (Class<?> clazz : applicableNeighborTypes) {
                if (block.getClass().isInstance(clazz)) {
                    ArrayUtils.add(applicableNeighbors, neighbor);
                }
            }
        }
        return applicableNeighbors;
    }

    /**
     * Returns the closest block from listOfBlocks to thisBlock.
     * @param thisBlock block to check from
     * @param listOfBlocks loops through list to find closest
     * @return closest block pos from list to block
     */
    public static BlockPos getClosest(BlockPos thisBlock, List<BlockPos> listOfBlocks) {
        BlockPos currentClosest = null;
        for (BlockPos pos : listOfBlocks) {
            double dstntSq = thisBlock.distanceSq(pos);
            if (currentClosest != null) {
                if (dstntSq < pos.distanceSq(currentClosest)) {
                    currentClosest = pos;
                }
            } else currentClosest = pos;
        }
        return currentClosest;
    }

    /**
     * Checks if the position given is the highest on the y coordinates.
     * @param world The world
     * @param pos Position of the block
     * @return Is the position representing the highest block on the y coordinate of the position
     */
    public static boolean isBlockHighest(World world, BlockPos pos) {
        return world.getTopSolidOrLiquidBlock(pos).equals(pos);
    }

}
