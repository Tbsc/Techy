package tbsc.techy.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.api.ITechyRotatable;

import java.util.Random;

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
    public static PropertyBool NORTH = PropertyBool.create("north");
    public static PropertyBool SOUTH = PropertyBool.create("south");
    public static PropertyBool WEST = PropertyBool.create("west");
    public static PropertyBool EAST = PropertyBool.create("east");
    public static PropertyBool UP = PropertyBool.create("up");
    public static PropertyBool DOWN = PropertyBool.create("down");

    protected BlockBaseFacingMachine(Material material, String registryName, int tileInvSize) {
        super(material, registryName, tileInvSize);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
        .withProperty(NORTH, false)
        .withProperty(SOUTH, false)
        .withProperty(WEST, false)
        .withProperty(EAST, false)
        .withProperty(UP, false)
        .withProperty(DOWN, false));
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
     * Checks if block can be changed
     * @param pos position of block
     * @return is correct block
     */
    public static boolean isCorrectBlock(World world, BlockPos pos, Class<? extends BlockBaseFacingMachine> clazz) {
        IBlockState iblockstate = world.getBlockState(pos);

        return clazz.isInstance(iblockstate.getBlock());
    }

    @Override
    public int getLightValue(IBlockState state) {
        if (state.getProperties().containsKey(WORKING)) {
            return state.getValue(WORKING) ? 12 : 0;
        }
        return 0;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getProperties().containsKey(WORKING)) {
            if (stateIn.getValue(WORKING)) {
                EnumFacing facing = stateIn.getValue(FACING);
                double d0 = (double) pos.getX() + 0.3D;
                double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
                double d2 = (double) pos.getZ() + 0.3D;
                double d3 = 0.52D;
                double d4 = rand.nextDouble() * 0.6D - 0.3D;

                if (rand.nextDouble() < 0.1D) {
                    worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }

                switch (facing) {
                    case WEST:
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                        break;
                    case EAST:
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                        break;
                    case NORTH:
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                        break;
                    case SOUTH:
                        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
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
        // Don't know of a better way to do this
        switch (state.getValue(FACING)) {
            case NORTH:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case SOUTH:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case WEST:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            case EAST:
                return state.withProperty(NORTH, canConnectOnSide(worldIn, pos, EnumFacing.EAST))
                        .withProperty(SOUTH, canConnectOnSide(worldIn, pos, EnumFacing.WEST))
                        .withProperty(WEST, canConnectOnSide(worldIn, pos, EnumFacing.NORTH))
                        .withProperty(EAST, canConnectOnSide(worldIn, pos, EnumFacing.SOUTH))
                        .withProperty(UP, canConnectOnSide(worldIn, pos, EnumFacing.UP))
                        .withProperty(DOWN, canConnectOnSide(worldIn, pos, EnumFacing.DOWN));
            default:
                return state; // No special connections, better to solve it this way rather then bad connections
        }
    }

    /**
     * The implementation of this method should return whether this block can connect to
     * nearby blocks.
     * @param world to check the nearby blocks
     * @param pos position of this block
     * @param side to check if is connectible
     * @return is side connectible with block
     */
    public abstract boolean canConnectOnSide(IBlockAccess world, BlockPos pos, EnumFacing side);

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
        return new BlockStateContainer(this, FACING, WORKING, NORTH, SOUTH, WEST, EAST, UP, DOWN);
    }

}
