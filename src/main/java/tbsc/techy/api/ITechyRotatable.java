package tbsc.techy.api;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.EnumFacing;

/**
 * Implemented by blocks that can be rotated. Used by the Techy wrench for giving the
 * block the ability to rotate.
 *
 * Created by tbsc on 3/27/16.
 */
public interface ITechyRotatable {

    /**
     * Used by the wrench to rotate blocks.
     *
     * @param block The block about to be rotated
     * @param rotateTo What direction to rotate the block to (This direction is the new FRONT of the block!)
     * @param simulate whether to simulate the action
     * @return if rotation was successful
     */
    boolean rotateBlock(BlockState block, EnumFacing rotateTo, boolean simulate);

    /**
     * Checks if the block can be rotated.
     *
     * @param block The block in question
     * @param rotateTo The new front to rotate to
     * @return If can rotate
     */
    boolean canRotate(BlockState block, EnumFacing rotateTo);

}
