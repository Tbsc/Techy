package cofh.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

/**
 * Implement this interface on blocks which have some debug method which can be activated via a tool or other means.
 *
 * @author King Lemming
 *
 */
public interface IBlockDebug {

	/**
	 * This function debugs a block.
	 *
	 * @param world
	 *            Reference to the world.
	 * @param pos
	 *            Coordinates of the block.
	 * @param side
	 *            The side of the block.
	 * @param player
	 *            Player doing the debugging.
	 */
	void debugBlock(IBlockAccess world, BlockPos pos, EnumFacing side, EntityPlayer player);

}
