package tbsc.techy.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

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
     * @param world the world in which this is happening
     * @param pos Position of the block
     * @param rotateTo What direction to rotate the block to (This direction is the new FRONT of the block!)
     * @param placedBy the placer of the block
     * @param simulate whether to simulate the action
     * @return if rotation was successful
     */
    void rotateBlock(IBlockState block, World world, BlockPos pos, EnumFacing rotateTo, EntityLivingBase placedBy, boolean simulate);

}
