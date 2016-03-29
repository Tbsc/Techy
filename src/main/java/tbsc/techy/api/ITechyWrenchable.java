package tbsc.techy.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * This interface is used by the Techy wrench to detect whether blocks are wrenchable, and
 * provides the wrench and the block ability to choose what will happen when wrenching.
 * NOTE: This interface needs to be implemented in the BLOCK!
 *
 * Created by tbsc on 3/27/16.
 */
public interface ITechyWrenchable {

    /**
     * The block needs to implement this method in order for the block to be wrenchable.
     *
     * @param block Block to be dismantled
     * @param world the world instance
     * @param pos Position of the block
     * @param wrenchedBy The player who wrenched the block
     * @return successful
     */
    void dismantleBlock(IBlockState block, World world, BlockPos pos, EntityLivingBase wrenchedBy);

    /**
     * The block needs to return whether it can be dismantled ATM and return accordingly.
     * The block should *NOT* check whether the player is sneaking, as this is done by the wrench itself.
     *
     * @param block The block in question
     * @param world the world instance
     * @param pos Position of the block
     * @param wrenchedBy The player who wrenched the block
     * @return Whether it can be dismantled
     */
    boolean canDismantle(IBlockState block, World world, BlockPos pos, EntityLivingBase wrenchedBy);

}
