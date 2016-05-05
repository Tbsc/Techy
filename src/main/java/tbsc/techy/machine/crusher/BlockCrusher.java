package tbsc.techy.machine.crusher;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tbsc.techy.Techy;
import tbsc.techy.api.ITechyWrench;
import tbsc.techy.block.BlockBaseFacingMachine;

import java.util.ArrayList;

/**
 * Crusher's block class.
 *
 * Created by tbsc on 5/4/16.
 */
public class BlockCrusher extends BlockBaseFacingMachine {

    public static int tileInvSize = 8;

    public BlockCrusher() {
        super(Material.iron, "blockCrusher", tileInvSize);
    }

    /**
     * Gets called when a player hits the block (right click).
     * @param worldIn world
     * @param pos position of the block
     * @param state the block(state)
     * @param playerIn the player that did the action
     * @param side the side that got hit
     * @param hitX the position (on the x axis) that got hit on the block
     * @param hitY the position (on the y axis) that got hit on the block
     * @param hitZ the position (on the z axis) that got hit on the block
     * @return i have no idea, darn you obfuscation
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem() != null && playerIn.getHeldItem().getItem() instanceof ITechyWrench) {
            return false;
        }
        if (!worldIn.isRemote) {
            playerIn.openGui(Techy.instance, Techy.CRUSHER_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, BlockPos pos, boolean returnDrops) {
        dismantleBlock(world.getBlockState(pos), world, pos, player);
        return null;
    }

    @Override
    public boolean canDismantle(EntityPlayer player, World world, BlockPos pos) {
        return canDismantle(world.getBlockState(pos), world, pos, player);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCrusher();
    }
}
