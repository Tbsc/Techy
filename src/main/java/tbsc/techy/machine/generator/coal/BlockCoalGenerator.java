package tbsc.techy.machine.generator.coal;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.Techy;
import tbsc.techy.api.ITechyWrench;
import tbsc.techy.block.BlockBaseFacingMachine;

import javax.annotation.Nullable;

/**
 * Coal generator for RF energy
 *
 * Created by tbsc on 5/20/16.
 */
public class BlockCoalGenerator extends BlockBaseFacingMachine {

    public BlockCoalGenerator() {
        super(Material.CIRCUITS, "blockCoalGenerator", 5);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCoalGenerator();
    }

    @Override
    public boolean canConnectOnSide(IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (world.getTileEntity(pos.offset(side)) != null) {
            if (world.getTileEntity(pos.offset(side)) instanceof IEnergyHandler || world.getTileEntity(pos.offset(side)) instanceof IInventory) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null && heldItem.getItem() instanceof ITechyWrench) {
            return false;
        }
        if (!worldIn.isRemote) {
            playerIn.openGui(Techy.instance, Techy.COAL_GENERATOR_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

}
