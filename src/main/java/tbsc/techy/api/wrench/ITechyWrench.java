package tbsc.techy.api.wrench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Item interface, marks items as wrenches.
 * Any item that implements this should add support for rotating and dismantling.
 * If the item wants to remove a functionality, it can just return false in either
 * check methods for ability to wrench/rotate.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface ITechyWrench {

    /**
     * Checks if the entity holding the wrench can rotate the target at the position given, using the item.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return Can the entity rotate the target using the item
     */
    boolean canRotate(ItemStack wrench, EntityLivingBase entity, BlockPos target);

    /**
     * Rotates the target "using" the item wrench given.
     * This method should NEVER check if it can rotate before rotating. The check is left
     * for the user to do.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return If the rotation succeeded
     */
    Result rotate(ItemStack wrench, EntityLivingBase entity, BlockPos target);

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
