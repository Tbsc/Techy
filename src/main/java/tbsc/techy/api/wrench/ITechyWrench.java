/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.api.wrench;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
