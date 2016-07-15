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

import cofh.api.block.IDismantleable;
import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.wrench.Result;

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
     * Calls {@link #dismantle(EntityLivingBase, World, BlockPos)}.
     * @param player The player
     * @param world The world
     * @param x X position
     * @param y Y position
     * @param z Z position
     * @param returnDrops Should return drops to the player's inventory, ignored
     * @return Empty list
     */
    @Override
    default ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops) {
        dismantle(player, world, new BlockPos(x, y, z));
        return Lists.newArrayList();
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
     * @return Is the dismantle successful
     */
    Result dismantle(EntityLivingBase dismantler, World world, BlockPos target);

}
