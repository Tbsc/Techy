package tbsc.techy.api.capability.wrench;

import cofh.api.item.IToolHammer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import tbsc.techy.api.wrench.Result;

/**
 * Item interface, marks items as wrenches.
 * Any item that implements this should add support for rotating and dismantling.
 * If the item wants to remove a functionality, it can just return false in either
 * check methods for ability to wrench/rotate.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface ITechyWrench extends IToolHammer {

    /**
     * Default implementation of the CoFH tool hammer API, for Techy wrenches.
     * Just calls the Techy methods.
     * @param item
     *            The itemstack for the tool. Not required to match equipped item (e.g., multi-tools that contain other tools)
     * @param user
     *            The entity using the tool
     * @param x
     *            X location of the block/tile
     * @param y
     *            Y location of the block/tile
     * @param z
     *            Z location of the block/tile
     * @return Is the tool usable
     */
    @Override
    default boolean isUsable(ItemStack item, EntityLivingBase user, int x, int y, int z) {
        return canDismantle(item, user, new BlockPos(x, y, z));
    }

    /**
     * Default implementation of the CoFH tool hammer API, for Techy wrenches.
     * @param item
     *            The ItemStack for the tool. Not required to match equipped item (e.g., multi-tools that contain other tools)
     * @param user
     *            The entity using the tool
     * @param x
     *            X location of the block/tile
     * @param y
     *            Y location of the block/tile
     * @param z
     *            Z location of the block/tile
     */
    @Override
    default void toolUsed(ItemStack item, EntityLivingBase user, int x, int y, int z) {
        dismantle(item, user, new BlockPos(x, y, z));
    }

    /**
     * Checks if the entity holding the wrench can rotate the target at the position given, using the item.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @param rotateTo Side to rotate the target to
     * @return Can the entity rotate the target using the item
     */
    boolean canRotate(ItemStack wrench, EntityLivingBase entity, BlockPos target, EnumFacing rotateTo);

    /**
     * Rotates the target "using" the item wrench given.
     * This method should NEVER check if it can rotate before rotating. The check is left
     * for the user to do.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @param rotateTo Side to rotate the target to
     * @return If the rotation succeeded
     */
    Result rotate(ItemStack wrench, EntityLivingBase entity, BlockPos target, EnumFacing rotateTo);

    /**
     * Checks if the entity holding the wrench can dismantle the target at the position given, using the item.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return Can the entity dismantle the target using the item
     */
    boolean canDismantle(ItemStack wrench, EntityLivingBase entity, BlockPos target);

    /**
     * Dismantles the target "using" the item wrench given.
     * This method should NEVER check if it can dismantle before dismantling. The check is left
     * for the user to do.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return If the dismantle succeeded
     */
    Result dismantle(ItemStack wrench, EntityLivingBase entity, BlockPos target);

}
