package tbsc.techy.api;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.apache.commons.lang3.ArrayUtils;

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

}
