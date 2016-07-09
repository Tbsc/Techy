package tbsc.techy.api.wrench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This interface should be implemented on anything that can be dismantled. Note that not only
 * blocks can be dismantled, but also entities.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface ITechyDismantleable {

    /**
     * Checks if the target can be dismantled.
     * @param dismantler The entity dismantling
     * @param world The world it's happening in
     * @param target The position of this
     * @return Can be dismantled
     */
    boolean isDismantleable(EntityLivingBase dismantler, World world, BlockPos target);

    /**
     * Dismantle the block at the target.
     * NOTE: Checking {@link #isDismantleable(EntityLivingBase, World, BlockPos)} SHOULD NOT
     * be done here.
     * @param dismantler The entity dismantling
     * @param world The world instance
     * @param target The block to dismantle
     * @return Is the dismantle successful
     */
    Result dismantle(EntityLivingBase dismantler, World world, BlockPos target);

}
