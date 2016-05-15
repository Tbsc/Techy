package tbsc.techy.block.pipe;

import cofh.api.energy.IEnergyConnection;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.pipe.client.PipeEnergyISBM;
import tbsc.techy.tile.pipe.TilePipeEnergy;

/**
 * Block for the energy pipe
 *
 * Created by tbsc on 5/9/16.
 */
public class BlockPipeEnergy extends BlockPipeBase {

    public BlockPipeEnergy() {
        super("blockPipeEnergy", 0);
    }

    @Override
    public boolean canConnectOnSide(IBlockAccess world, BlockPos thisBlock, EnumFacing side) {
        return canConnectWithBlock(world, thisBlock.offset(side));
    }

    /**
     * Inits the ISBM
     */
    @SideOnly(Side.CLIENT)
    public void initModel() {
        // To make sure that our ISBM model is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return PipeEnergyISBM.modelResourceLocation;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }

    @Override
    public boolean canConnectWithBlock(IBlockAccess world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        TileEntity tile = world.getTileEntity(blockPos);
        return (tile != null && tile instanceof IEnergyConnection) || block instanceof BlockPipeEnergy; // If the block can connect to energy stuff, then we can connect to it
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeEnergy();
    }

}
