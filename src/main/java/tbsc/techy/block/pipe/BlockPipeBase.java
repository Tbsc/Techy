package tbsc.techy.block.pipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;
import tbsc.techy.block.BlockBaseMachine;

/**
 * Block for the basic pipe. It is protected because it isn't an actual pipe, but rather
 * it should be extended as real pipes.
 *
 * Created by tbsc on 5/9/16.
 */
public abstract class BlockPipeBase extends BlockBaseMachine {

    public static final PropertyConnection NORTH_CONNECTION = PropertyConnection.create("north_connection");
    public static final PropertyConnection SOUTH_CONNECTION = PropertyConnection.create("up_connection");
    public static final PropertyConnection WEST_CONNECTION = PropertyConnection.create("west_connection");
    public static final PropertyConnection EAST_CONNECTION = PropertyConnection.create("east_connection");
    public static final PropertyConnection UP_CONNECTION = PropertyConnection.create("up_connection");
    public static final PropertyConnection DOWN_CONNECTION = PropertyConnection.create("down_connection");

    protected BlockPipeBase(String registryName, int tileInvSize) {
        super(Material.circuits, registryName, tileInvSize);
        setHardness(2.0F);
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
     * @param block to be checked
     * @return if it can connect with the block
     */
    public abstract boolean canConnectWithBlock(Block block);

    /**
     * Inits the item model for the pipe, so it'd render in hand like it does when placed
     */
    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        // For our item model we want to use a normal json model. This has to be called in
        // ClientProxy.init (not preInit) so that's why it is a separate method.
        Item itemBlock = GameRegistry.findItem(Techy.MODID, getRegistryName());
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    }

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

    /**
     * Tells the game to not render any sides, as it is done by the ISBM
     * @param worldIn the world
     * @param pos of the pipe
     * @param side side to render
     * @return if the side should be rendered (false)
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    /**
     * Is the block a normal cube
     * @return false
     */
    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    /**
     * Is the block opaque
     * @return false
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Returns the extended block state of the block, containing the connection properties.
     * @param state of the block
     * @param world the world
     * @param pos of the block
     * @return extended state with connection properties
     */
    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        boolean north = canConnectOnSide(world, pos, EnumFacing.NORTH);
        boolean south = canConnectOnSide(world, pos, EnumFacing.SOUTH);
        boolean west = canConnectOnSide(world, pos, EnumFacing.WEST);
        boolean east = canConnectOnSide(world, pos, EnumFacing.EAST);
        boolean up = canConnectOnSide(world, pos, EnumFacing.UP);
        boolean down = canConnectOnSide(world, pos, EnumFacing.DOWN);

        return extendedBlockState
                .withProperty(NORTH_CONNECTION, north)
                .withProperty(SOUTH_CONNECTION, south)
                .withProperty(WEST_CONNECTION, west)
                .withProperty(EAST_CONNECTION, east)
                .withProperty(UP_CONNECTION, up)
                .withProperty(DOWN_CONNECTION, down);
    }

    /**
     * Returns a block state with the applicable properties
     * @return block state container
     */
    @Override
    protected BlockState createBlockState() {
        IUnlistedProperty[] unlistedProps = new IUnlistedProperty[] { NORTH_CONNECTION, SOUTH_CONNECTION, WEST_CONNECTION, EAST_CONNECTION, UP_CONNECTION, DOWN_CONNECTION };
        IProperty[] props = new IProperty[0];
        return new ExtendedBlockState(this, props, unlistedProps);
    }

    public static class PropertyConnection implements IUnlistedProperty<Boolean> {

        private final String name;

        private PropertyConnection(String name) {
            this.name = name;
        }

        public static PropertyConnection create(String name) {
            return new PropertyConnection(name);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isValid(Boolean value) {
            return true;
        }

        @Override
        public Class<Boolean> getType() {
            return Boolean.class;
        }

        @Override
        public String valueToString(Boolean value) {
            return Boolean.toString(value);
        }

    }

}
