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

package tbsc.techy.api.capability.dismantle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.wrench.Result;

/**
 * Default implementaion for {@link ITechyDismantleable}.
 *
 * Created by tbsc on 11/07/2016.
 */
public class TechyDismantleable implements ITechyDismantleable {

    /**
     * Checks if the entity can dismantle the block.
     * What is does is see if the entity is in range of 8 blocks from the target.
     * @param dismantler The entity dismantling
     * @param world The world it's happening in
     * @param target The position of this
     * @return Can the target be dismantled
     */
    @Override
    public boolean isDismantleable(EntityLivingBase dismantler, World world, BlockPos target) {
        return dismantler.getDistanceSq(target) >= 64 && world.isBlockLoaded(target); // Is in 8 block range and is loaded (also checking if exists)
    }

    /**
     * Basic implementation, what it does is call {@link Block#breakBlock(World, BlockPos, IBlockState)}.
     * This is just the baseline implementation. If something wants that something else will happen, then
     * extend this class and change the behaviour.
     * @param dismantler The entity dismantling
     * @param world The world instance
     * @param target The block to dismantle
     * @return Has the dismantling been successful. Always returns yes in this case
     */
    @Override
    public Result dismantle(EntityLivingBase dismantler, World world, BlockPos target) {
        IBlockState blockState = world.getBlockState(target);
        blockState.getBlock().breakBlock(world, target, blockState);
        world.destroyBlock(target, true);
        return Result.SUCCESS;
    }

}
