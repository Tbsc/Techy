package tbsc.techy.block.pipe;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.block.BlockBaseMachine;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base block for pipes. It is protected because it isn't an actual pipe, but rather
 * should be extended as real pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public abstract class BlockPipeBase extends BlockBaseMachine {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    public static AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.29, 0.29, 0.29, 0.71, 0.71, 0.71);
    public static AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.29, 0.29, 0, 0.71, 0.71, 0.29);
    public static AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.29, 0.29, 0.71, 0.71, 0.71, 1);
    public static AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.29, 0.29, 0.29, 0, 0.71, 0.71);
    public static AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.71, 0.29, 0.29, 1, 1, 0.71);
    public static AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.29, 0, 0.29, 0.71, 0.29, 0.71);
    public static AxisAlignedBB UP_AABB = new AxisAlignedBB(0.29, 0.71, 0.29, 0.71, 1, 0.71);

    /**
     * Used to know the type of class a block needs to be a sub type of to connect to
     */
    protected Class<?> connectiblePipeClass;

    protected BlockPipeBase(String registryName, int tileInvSize, Class<?> connectiblePipeClass) {
        super(Material.CIRCUITS, registryName, tileInvSize);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());

        this.connectiblePipeClass = connectiblePipeClass;
        setDefaultState(blockState.getBaseState().withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false).withProperty(UP, false).withProperty(DOWN, false));
    }

    /**
     * Checks if it can connect with the block on the side given.
     * The block isn't known yet, it just tells the class to check if it can connect there.
     * @param side to check
     * @return if it can connect on given side
     */
    public abstract boolean canConnectOnSide(IBlockAccess world, BlockPos thisBlock, EnumFacing side);

    /**
     * Checks if the pipe can connect with the block given.
     * @param world the world
     * @param block to be checked
     * @return if it can connect with the block
     */
    public abstract boolean canConnectWithBlock(IBlockAccess world, BlockPos block);

    /**
     * Update nearby blocks when block is placed for them to know a pipe is placed, and if they are a pipe too
     * then to connect
     * @param world the world
     * @param pos of the pipe
     * @param state of the pipe
     * @param placer who placed the block
     * @param stack the stack the block was placed from
     */
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.markBlockRangeForRenderUpdate(pos.add(-1, -1, -1), pos.add(1, 1, 1));
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE_AABB);
        if (state.getValue(NORTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }
        if (state.getValue(SOUTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }
        if (state.getValue(WEST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
        if (state.getValue(EAST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }
        if (state.getValue(DOWN)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_AABB);
        }
        if (state.getValue(UP)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
        }
    }

    /*
    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        RayTraceResult result = new RayTraceResult(pos.);
        if (result != null) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                if (BoundingBoxUtil.isInSameLocation(result.hitVec, NORTH_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.NORTH;
                } else if (BoundingBoxUtil.isInSameLocation(result.hitVec, SOUTH_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.SOUTH;
                } else if (BoundingBoxUtil.isInSameLocation(result.hitVec, WEST_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.WEST;
                } else if (BoundingBoxUtil.isInSameLocation(result.hitVec, EAST_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.EAST;
                } else if (BoundingBoxUtil.isInSameLocation(result.hitVec, UP_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.UP;
                } else if (BoundingBoxUtil.isInSameLocation(result.hitVec, DOWN_AABB.offset(pos))) {
                    result.hitInfo = EnumFacing.DOWN;
                }
            }
        }
        return result;
    }*/

    /**
     * Returns a bounding box for the pipe.
     * connections using block models.
     * @param state the block state
     * @param source access to the world
     * @param pos the position of the block
     * @return the bounding box for this pipe
     */
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BASE_AABB;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        /*
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            double reachDistance = (double) Minecraft.getMinecraft().playerController.getBlockReachDistance();
            RayTraceResult rayTrace = player.rayTrace(reachDistance, 1.0F);

            if (rayTrace != null) {
                switch ((EnumFacing) rayTrace.hitInfo) {
                    case NORTH:
                        return NORTH_AABB;
                    case SOUTH:
                        return SOUTH_AABB;
                    case WEST:
                        return WEST_AABB;
                    case EAST:
                        return EAST_AABB;
                    case UP:
                        return UP_AABB;
                    case DOWN:
                        return DOWN_AABB;
                }
            }
        }
        */

        return BASE_AABB;
    }

    /**
     * Is the block a normal cube
     * @return false
     */
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    /**
     * Is the block opaque
     * @return false
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        boolean north = canConnectOnSide(world, pos, EnumFacing.NORTH);
        boolean south = canConnectOnSide(world, pos, EnumFacing.SOUTH);
        boolean west = canConnectOnSide(world, pos, EnumFacing.WEST);
        boolean east = canConnectOnSide(world, pos, EnumFacing.EAST);
        boolean up = canConnectOnSide(world, pos, EnumFacing.UP);
        boolean down = canConnectOnSide(world, pos, EnumFacing.DOWN);

        return state
                .withProperty(NORTH, north)
                .withProperty(SOUTH, south)
                .withProperty(WEST, west)
                .withProperty(EAST, east)
                .withProperty(UP, up)
                .withProperty(DOWN, down);
    }

    /**
     * Returns a block state with the applicable properties
     * @return block state container
     */
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, SOUTH, WEST, EAST, UP, DOWN);
    }

}
