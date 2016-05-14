package tbsc.techy.tile.pipe;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.property.IExtendedBlockState;
import tbsc.techy.block.pipe.BlockPipeBase;

/**
 * Base tile entity for pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public abstract class TilePipeBase extends TileEntity implements ITickable {

    protected TilePipeBase() {

    }

    @Override
    public void update() {

    }

    /**
     * Checks neighbor blocks if they are able to connect, and if they are then it connects
     * to them.
     */
    public void onConnectableNeighborPlaced() {
        IExtendedBlockState state = (IExtendedBlockState) worldObj.getBlockState(pos);
        BlockPipeBase pipe = (BlockPipeBase) state.getBlock();
        for (EnumFacing side : EnumFacing.VALUES) {
            IExtendedBlockState neighbor = null;
            BlockPipeBase.PropertyConnection sideProperty = null;
            switch (side) {
                case NORTH:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.north());
                    sideProperty = BlockPipeBase.NORTH_CONNECTION;
                    break;
                case SOUTH:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.south());
                    sideProperty = BlockPipeBase.SOUTH_CONNECTION;
                    break;
                case WEST:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.west());
                    sideProperty = BlockPipeBase.WEST_CONNECTION;
                    break;
                case EAST:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.east());
                    sideProperty = BlockPipeBase.EAST_CONNECTION;
                    break;
                case UP:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.up());
                    sideProperty = BlockPipeBase.UP_CONNECTION;
                    break;
                case DOWN:
                    neighbor = (IExtendedBlockState) worldObj.getBlockState(pos.down());
                    sideProperty = BlockPipeBase.DOWN_CONNECTION;
                    break;
            }
            if (neighbor != null) {
                if (neighbor.getBlock() != null) {
                    if (pipe.canConnectWithBlock(neighbor.getBlock()) && sideProperty != null) {
                        state = state.withProperty(sideProperty, true);
                    }
                }
            }
        }
        worldObj.setBlockState(pos, state);
    }

}
