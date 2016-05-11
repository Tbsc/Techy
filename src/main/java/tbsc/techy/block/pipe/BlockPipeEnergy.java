package tbsc.techy.block.pipe;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tbsc.techy.tile.pipe.TilePipeEnergy;

/**
 * Block for the energy pipe
 *
 * Created by tbsc on 5/9/16.
 */
public class BlockPipeEnergy extends BlockPipeBase {

    protected BlockPipeEnergy() {
        super("blockPipeEnergy", 0);
    }

    @Override
    public boolean canConnectOnSide(World world, BlockPos thisBlock, EnumFacing side) {
        switch (side) {
            case NORTH:
                return canConnectWithBlock(world.getBlockState(thisBlock.north()).getBlock());
            case SOUTH:
                return canConnectWithBlock(world.getBlockState(thisBlock.south()).getBlock());
            case WEST:
                return canConnectWithBlock(world.getBlockState(thisBlock.west()).getBlock());
            case EAST:
                return canConnectWithBlock(world.getBlockState(thisBlock.east()).getBlock());
            case UP:
                return canConnectWithBlock(world.getBlockState(thisBlock.up()).getBlock());
            case DOWN:
                return canConnectWithBlock(world.getBlockState(thisBlock.down()).getBlock());
            default:
                return false;
        }
    }

    @Override
    public boolean canConnectWithBlock(Block block) {
        return block instanceof IEnergyReceiver; // If the block can receive energy, then we can connect to it
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeEnergy();
    }

}
