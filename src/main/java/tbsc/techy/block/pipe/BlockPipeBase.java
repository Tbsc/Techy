/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import java.util.EnumMap;
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

    public static EnumMap<EnumFacing, AxisAlignedBB> aabbMap = new EnumMap<>(EnumFacing.class);

    static {
        aabbMap.put(EnumFacing.NORTH, new AxisAlignedBB(0.295, 0.295, 0, 0.705, 0.705, 0.295));
        aabbMap.put(EnumFacing.SOUTH, new AxisAlignedBB(0.295, 0.295, 0.705, 0.705, 0.705, 1));
        aabbMap.put(EnumFacing.WEST, new AxisAlignedBB(0.295, 0.295, 0.295, 0, 0.705, 0.705));
        aabbMap.put(EnumFacing.EAST, new AxisAlignedBB(0.705, 0.295, 0.295, 1, 1, 0.705));
        aabbMap.put(EnumFacing.UP, new AxisAlignedBB(0.295, 0.705, 0.295, 0.705, 1, 0.705));
        aabbMap.put(EnumFacing.DOWN, new AxisAlignedBB(0.295, 0, 0.295, 0.705, 0.295, 0.705));
    }

    /**
     * Center piece
     */
    public static AxisAlignedBB BASE_AABB = new AxisAlignedBB(0.295, 0.295, 0.295, 0.705, 0.705, 0.705);

    /**
     * Used to know the type of class a block needs to be a sub type of to connect to
     */
    protected Class<?> connectiblePipeClass;

    protected BlockPipeBase(String registryName, int tileInvSize, Class<?> connectiblePipeClass) {
        super(Material.CIRCUITS, registryName, tileInvSize);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
//        MinecraftForge.EVENT_BUS.register(this);

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
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.NORTH));
        }
        if (state.getValue(SOUTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.SOUTH));
        }
        if (state.getValue(WEST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.WEST));
        }
        if (state.getValue(EAST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.EAST));
        }
        if (state.getValue(UP)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.UP));
        }
        if (state.getValue(DOWN)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, aabbMap.get(EnumFacing.DOWN));
        }
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        /*
        if (world.isRemote) {
            RayTraceResult rayTraceResult = Minecraft.getMinecraft().thePlayer.rayTrace(Minecraft.getMinecraft().playerController.getBlockReachDistance(), 1.0F);

            if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                AxisAlignedBB box = new AxisAlignedBB(BASE_AABB.minX, BASE_AABB.minY, BASE_AABB.minZ, BASE_AABB.maxX, BASE_AABB.maxY, BASE_AABB.maxZ);
                Vec3d hitVec = rayTraceResult.hitVec;
                if (hitVec.xCoord > NORTH_AABB.minX && hitVec.xCoord < NORTH_AABB.maxX &&
                        hitVec.yCoord > NORTH_AABB.minY && hitVec.yCoord < NORTH_AABB.maxY &&
                        hitVec.zCoord > NORTH_AABB.minZ && hitVec.zCoord < NORTH_AABB.maxZ) {
                    FMLLog.info("hitvec is inside north aabb");
                    return NORTH_AABB;
                }
                if (SOUTH_AABB.isVecInside(hitVec)) {
                    return SOUTH_AABB;
                }
                if (WEST_AABB.isVecInside(hitVec)) {
                    return WEST_AABB;
                }
                if (EAST_AABB.isVecInside(hitVec)) {
                    return EAST_AABB;
                }
                if (UP_AABB.isVecInside(hitVec)) {
                    return UP_AABB;
                }
                if (DOWN_AABB.isVecInside(hitVec)) {
                    return DOWN_AABB;
                }
                return box.offset(pos.getX(), pos.getY(), pos.getZ());
            }
            return super.getSelectedBoundingBox(state, world, pos).expand(-0.85F, -0.85F, -0.85F);
        }
        return super.getSelectedBoundingBox(state, world, pos);
        */
        return getBoundingBox(state, world, pos);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        AxisAlignedBB box = new AxisAlignedBB(BASE_AABB.minX, BASE_AABB.minY, BASE_AABB.minZ, BASE_AABB.maxX, BASE_AABB.maxY, BASE_AABB.maxZ); // copy

        if (state.getValue(NORTH)) {
            box = box.union(aabbMap.get(EnumFacing.NORTH));
        }
        if (state.getValue(SOUTH)) {
            box = box.union(aabbMap.get(EnumFacing.SOUTH));
        }
        if (state.getValue(WEST)) {
            box = box.union(aabbMap.get(EnumFacing.WEST));
        }
        if (state.getValue(EAST)) {
            box = box.union(aabbMap.get(EnumFacing.EAST));
        }
        if (state.getValue(UP)) {
            box = box.union(aabbMap.get(EnumFacing.UP));
        }
        if (state.getValue(DOWN)) {
            box = box.union(aabbMap.get(EnumFacing.DOWN));
        }

        return box;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos);
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

    /*
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDrawBlockOutline(DrawBlockHighlightEvent event) {
        RayTraceResult hit = event.getTarget();
        if (hit != null) {
            GlStateManager.pushMatrix(); // Begin render

            BlockPos pos = hit.getBlockPos();
            EntityPlayer player = event.getPlayer();
            float partialTicks = event.getPartialTicks();
            double x = pos.getX() - (player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks);
            double y = pos.getY() - (player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks);
            double z = pos.getZ() - (player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks);
            GlStateManager.translate(x, y, z);

            // render outline
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
            GlStateManager.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);

            RenderGlobal.drawSelectionBoundingBox(player.worldObj.getBlockState(pos).getSelectedBoundingBox(player.worldObj, pos).expandXyz(0.007));

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();

            GlStateManager.popMatrix(); // Stop render
        }
    }*/

}
