package tbsc.techy.block.pipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.block.BlockBaseMachine;
import tbsc.techy.tile.pipe.TilePipeBase;

/**
 * Block for the basic pipe. It is protected because it isn't an actual pipe, but rather
 * it should be extended as real pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public abstract class BlockPipeBase extends BlockBaseMachine {

    public static final PropertyBool NORTH_CONNECTION = PropertyBool.create("north_connection");
    public static final PropertyBool SOUTH_CONNECTION = PropertyBool.create("up_connection");
    public static final PropertyBool WEST_CONNECTION = PropertyBool.create("west_connection");
    public static final PropertyBool EAST_CONNECTION = PropertyBool.create("east_connection");
    public static final PropertyBool UP_CONNECTION = PropertyBool.create("up_connection");
    public static final PropertyBool DOWN_CONNECTION = PropertyBool.create("down_connection");

    protected BlockPipeBase(String registryName, int tileInvSize) {
        super(Material.circuits, registryName, tileInvSize);
        setHardness(2.0F);
        setDefaultState(blockState.getBaseState()
                .withProperty(NORTH_CONNECTION, false)
                .withProperty(SOUTH_CONNECTION, false)
                .withProperty(WEST_CONNECTION, false)
                .withProperty(EAST_CONNECTION, false)
                .withProperty(UP_CONNECTION, false)
                .withProperty(DOWN_CONNECTION, false));
    }

    /**
     * Checks if it can connect with the block on the side given.
     * The block isn't known yet, it just tells the class to check if it can connect there.
     * @param side to check
     * @return if it can connect on given side
     */
    public abstract boolean canConnectOnSide(World world, BlockPos thisBlock, EnumFacing side);

    /**
     * Checks if the pipe can connect with the block given.
     * @param block to be checked
     * @return if it can connect with the block
     */
    public abstract boolean canConnectWithBlock(Block block);

    /**
     * Performs a check on all side properties of the block and actually compares it to the actual world.
     * For example, if the block state says it is connected on the north side, but there isn't a block it
     * can connect to on the north side it sets the north connection property to false.
     * @param state of the block
     * @param worldIn block access
     * @param pos of the block
     * @return state with correct data
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (state.getValue(NORTH_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.NORTH)) {
            state = state.withProperty(NORTH_CONNECTION, false);
        } else if (!state.getValue(NORTH_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.NORTH)) {
            state = state.withProperty(NORTH_CONNECTION, true);
        }
        if (state.getValue(SOUTH_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.SOUTH)) {
            state = state.withProperty(SOUTH_CONNECTION, false);
        } else if (!state.getValue(SOUTH_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.SOUTH)) {
            state = state.withProperty(SOUTH_CONNECTION, true);
        }
        if (state.getValue(WEST_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.WEST)) {
            state = state.withProperty(WEST_CONNECTION, false);
        } else if (!state.getValue(WEST_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.WEST)) {
            state = state.withProperty(WEST_CONNECTION, true);
        }
        if (state.getValue(EAST_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.EAST)) {
            state = state.withProperty(EAST_CONNECTION, false);
        } else if (!state.getValue(EAST_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.EAST)) {
            state = state.withProperty(EAST_CONNECTION, true);
        }
        if (state.getValue(UP_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.UP)) {
            state = state.withProperty(UP_CONNECTION, false);
        } else if (!state.getValue(UP_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.UP)) {
            state = state.withProperty(UP_CONNECTION, true);
        }
        if (state.getValue(DOWN_CONNECTION) && !canConnectOnSide((World) worldIn, pos, EnumFacing.DOWN)) {
            state = state.withProperty(DOWN_CONNECTION, false);
        } else if (!state.getValue(DOWN_CONNECTION) && canConnectOnSide((World) worldIn, pos, EnumFacing.DOWN)) {
            state = state.withProperty(DOWN_CONNECTION, true);
        }

        return state;
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (canConnectWithBlock(neighborBlock)) {
            TilePipeBase tilePipe = (TilePipeBase) worldIn.getTileEntity(pos);
            tilePipe.onConnectableNeighborPlaced();
        }
    }

    getColl

    /**
     * Creates a new block state with the connection boolean properties
     * @return BlockState with properties
     */
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, NORTH_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION, EAST_CONNECTION, UP_CONNECTION, DOWN_CONNECTION);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * This is a pipe, and it isn't a normal cube
     * @return false
     */
    @Override
    public boolean isNormalCube() {
        return false;
    }

    /**
     * This isn't a full block, this is a pipe, and is smaller than a block
     * @return false
     */
    @Override
    public boolean isFullBlock() {
        return false;
    }
}
