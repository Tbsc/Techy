package tbsc.techy.tile.pipe;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Utility class for finding route for objects to pass through,
 * can also be used to just find the next pipe to go to
 *
 * Created by tbsc on 5/20/16.
 */
public class PipePathFinder {

    public static BlockPos findNextPipe(BlockPos currentPipe, BlockPos destination) {
        BlockPos nextPipe = null;
        for (EnumFacing side : EnumFacing.VALUES) {
            BlockPos pos = currentPipe.offset(side);
            if (nextPipe != null) {
                if (pos.distanceSq(destination) < nextPipe.distanceSq(destination)) {
                    nextPipe = pos;
                }
            } else nextPipe = pos;
        }
        return nextPipe;
    }

}
