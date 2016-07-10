package tbsc.techy.api.wrench;

import cofh.api.block.IDismantleable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * This interface should be implemented on anything that can be dismantled. Note that not only
 * blocks can be dismantled, but also entities.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface ITechyDismantleable extends IDismantleable {

    /**
     * Default implementation of CoFH's IDismantleable.
     * Calls {@link #dismantle(EntityLivingBase, World, BlockPos, boolean)}.
     * @param player The player
     * @param world The world
     * @param x X position
     * @param y Y position
     * @param z Z position
     * @param returnDrops Should return drops to the player's inventory
     * @return Block's drops
     */
    @Override
    default ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {
        return dismantle(player, world, new BlockPos(x, y, z), returnDrops).getDrops();
    }

    /**
     * Default implementation of CoFH's IDismantleable.
     * Calls {@link #isDismantleable(EntityLivingBase, World, BlockPos)}.
     * @param player The player
     * @param world The world
     * @param x X position
     * @param y Y position
     * @param z Z position
     * @return Can dismantle
     */
    @Override
    default boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
        return isDismantleable(player, world, new BlockPos(x, y, z));
    }

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
     * @param returnDrops Should return drops to the player's inventory
     * @return Is the dismantle successful, and what dropped
     */
    DismantleResult dismantle(EntityLivingBase dismantler, World world, BlockPos target, boolean returnDrops);

    class DismantleResult {

        private final ArrayList<ItemStack> drops;
        private final Result result;

        public DismantleResult(ArrayList<ItemStack> drops, Result result) {
            this.drops = drops;
            this.result = result;
        }

        public ArrayList<ItemStack> getDrops() { return drops; }

        public Result getResult() {
            return result;
        }

    }

}
