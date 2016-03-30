package tbsc.techy.machine.furnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tbsc.techy.Techy;
import tbsc.techy.block.BlockBasePoweredMachine;

/**
 * Created by tbsc on 3/27/16.
 */
public class BlockPoweredFurnace extends BlockBasePoweredMachine {

    public static int tileInvSize;

    public BlockPoweredFurnace() {
        super(Material.iron, "blockPoweredFurnace", 1); // TODO Change size when needed
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePoweredFurnace();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(Techy.instance, Techy.POWERED_FURNACE_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            // TODO Uncomment when GUIs are fixed
        }
        return true;
    }
}
