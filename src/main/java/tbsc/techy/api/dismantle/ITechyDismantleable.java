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

package tbsc.techy.api.dismantle;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.wrench.Result;

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
