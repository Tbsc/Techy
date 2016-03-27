package tbsc.techy.api;

import net.minecraft.block.state.IBlockState;

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
     * @param simulate should actually dismantle
     * @return successful
     */
    boolean dismantleBlock(IBlockState block, boolean simulate);

    /**
     * The block needs to return whether it can be dismantled ATM and return accordingly.
     *
     * @param block The block in question
     * @return Whether it can be dismantled
     */
    boolean canDismantle(IBlockState block);

}
