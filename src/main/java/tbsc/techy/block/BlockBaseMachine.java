package tbsc.techy.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.ITechyWrenchable;
import tbsc.techy.tile.TileBase;

import javax.annotation.Nonnull;

/**
 * Created by tbsc on 3/27/16.
 */
public abstract class BlockBaseMachine extends BlockBase implements ITileEntityProvider, ITechyWrenchable {

    public static int tileInvSize;

    public BlockBaseMachine(Material material, String registryName, int tileInvSize) {
        super(material, registryName);
        BlockBaseMachine.tileInvSize = tileInvSize;
    }

    protected TileBase getTileBase(World world, BlockPos pos) {
        return (TileBase) world.getTileEntity(pos);
    }

    @Override
    public void dismantleBlock(@Nonnull IBlockState block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityLivingBase wrenchedBy) {
        block.getBlock().breakBlock(world, pos, block);
    }

    @Override
    public boolean canDismantle(@Nonnull IBlockState block, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EntityLivingBase wrenchedBy) {
        return true;
    }

}
