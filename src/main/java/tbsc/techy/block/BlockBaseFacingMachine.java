package tbsc.techy.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.api.ITechyRotatable;

/**
 * This base class adds rotation logic to the machines, and most machines will be based on
 * this base class (if I don't change stuff too much).
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class BlockBaseFacingMachine extends BlockBaseMachine implements ITechyRotatable {

    /**
     * The direction the block is facing
     */
    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static PropertyBool WORKING = PropertyBool.create("working");
    /*
    public static PropertyBool NORTH = PropertyBool.create("north");
    public static PropertyBool SOUTH = PropertyBool.create("south");
    public static PropertyBool WEST = PropertyBool.create("west");
    public static PropertyBool EAST = PropertyBool.create("east");
    public static PropertyBool UP = PropertyBool.create("up");
    public static PropertyBool DOWN = PropertyBool.create("down");
    */

    protected BlockBaseFacingMachine(Material material, String registryName, int tileInvSize) {
        super(material, registryName, tileInvSize);
    }

    /**
     * Used to change the front texture of a block, based on a working property.
     * @param active the new value for the "working" property
     * @param worldIn the world
     * @param pos position of block
     */
    public static void setWorkingState(boolean active, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        worldIn.setBlockState(pos, iblockstate.withProperty(WORKING, active));
    }

    /**
     * Returns the working property for the state in the world and position given
     * @param world the world
     * @param pos position of block
     * @return value of working property
     */
    public static boolean getWorkingState(World world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos);

        return iblockstate.getValue(WORKING);
    }

    /**
     * Makes sure the block is facing the player when placed
     * @param worldIn the world
     * @param pos position of the block
     * @param state the block(state)
     * @param placer the (living) entity who placed the block
     * @param stack the stack that was placed
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        rotateBlock(state, worldIn, pos, placer.getHorizontalFacing().getOpposite(), placer); // Rotate the block to the correct rotation on place
    }

    /**
     * Rotates the block based on values given (mostly direction)
     * @param block The block about to be rotated
     * @param world the world in which this is happening
     * @param pos Position of the block
     * @param rotateTo What direction to rotate the block to (This direction is the new FRONT of the block!)
     * @param rotatedBy the placer of the block
     */
    @Override
    public void rotateBlock(IBlockState block, World world, BlockPos pos, EnumFacing rotateTo, EntityLivingBase rotatedBy) {
        if (EnumFacing.Plane.HORIZONTAL.apply(rotateTo)) { // Is this EnumFacing rotation valid for the horizontal plane
            world.setBlockState(pos, block.withProperty(FACING, rotateTo), 2); // Change rotation
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        /*
        // Don't know of a better way to do this
        switch (state.getValue(FACING)) {
            case NORTH:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case SOUTH:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case WEST:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case EAST:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            default:
                return state; // No special connections, better to solve it this way rather then bad connections
        }
        */
        return state; // Connections are disabled ATM until I get to fixing them, TODO
    }

    /*
    /**
     * The implementation of this method should return whether this block can connect to
     * nearby blocks.
     * @param world to check the nearby blocks
     * @param pos position of this block
     * @param side to check if is connectible
     * @return is side connectible with block
     *
    public abstract boolean canConnectOnSide(IBlockAccess world, BlockPos pos, EnumFacing side);
    */

    /**
     * Return the {@link IBlockState} of the block from metadata
     * @param meta metadata
     * @return {@link IBlockState} based on that metadata value
     */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getFront((meta & 3) + 2))
                .withProperty(WORKING, (meta & 8) != 0);
    }

    /**
     * Return metadata value from {@link IBlockState}
     * @param state {@link IBlockState} to be checked
     * @return the metadata for the {@link IBlockState}
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() + (state.getValue(WORKING) ? 8 : 0);
    }

    /**
     * Creates a new BlockState instance based on the block, that holds the property of the direction
     * the block is facing.
     * @return {@link BlockStateContainer} with {@link PropertyDirection} of {@code FACING} on the horizontal plane.
     */
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WORKING); //, NORTH, SOUTH, WEST, EAST, UP, DOWN);
    }

}
