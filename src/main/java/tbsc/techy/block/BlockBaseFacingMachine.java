package tbsc.techy.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tbsc.techy.api.ITechyRotatable;

/**
 * This base class adds rotation logic to the machines, and most machines will be based on
 * this base class (if I don't change stuff too much).
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class BlockBaseFacingMachine extends BlockBaseMachine implements ITechyRotatable {

    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockBaseFacingMachine(Material material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        rotateBlock(state, worldIn, pos, placer.getHorizontalFacing().getOpposite(), placer); // Rotate the block to the correct rotation on place
    }

    @Override
    public void rotateBlock(IBlockState blockState, World world, BlockPos pos, EnumFacing rotateTo, EntityLivingBase rotatedBy) {
        if (EnumFacing.Plane.HORIZONTAL.apply(rotateTo)) // Is this EnumFacing rotation valid for the horizontal plane
            world.setBlockState(pos, blockState.withProperty(FACING, rotateTo), 2); // Change rotation
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex()-2;
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

}
