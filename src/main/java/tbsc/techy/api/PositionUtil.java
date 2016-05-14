package tbsc.techy.api;

import net.minecraft.util.BlockPos;

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

}
