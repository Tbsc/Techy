package tbsc.techy.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.ITechyWrenchable;
import tbsc.techy.tile.TileBase;

import java.util.ArrayList;

/**
 * The base class for machines, that adds support for tile entities and wrenching.
 * 
 * Created by tbsc on 3/27/16.
 */
public abstract class BlockBaseMachine extends BlockBase implements ITileEntityProvider, ITechyWrenchable {

    public int tileInvSize;

    protected BlockBaseMachine(Material material, String registryName, int tileInvSize) {
        super(material, registryName);
        this.tileInvSize = tileInvSize;
        setHardness(5.0F);
    }

    /**
     * Returns the {@link TileBase} for this block, in this position.
     * @param world the world to check in
     * @param pos position of the block
     * @return the tile entity (in {@link TileBase} form.
     */
    protected TileBase getTileBase(World world, BlockPos pos) {
        return (TileBase) world.getTileEntity(pos);
    }

    /**
     * Dismantles (breaks) the block.
     * @param block Block to be dismantled
     * @param world the world instance
     * @param pos Position of the block
     * @param wrenchedBy The player who wrenched the block
     */
    @Override
    public void dismantleBlock(IBlockState block, World world, BlockPos pos, EntityLivingBase wrenchedBy) {
        block.getBlock().breakBlock(world, pos, block);
    }

    /**
     * Checks if the block can be dismantled. Right now it can be dismantled always, but it is there for
     * ability to add extra funtionality.
     * @param block The block in question
     * @param world the world instance
     * @param pos Position of the block
     * @param wrenchedBy The player who wrenched the block
     * @return state of ability to be dismantled
     */
    @Override
    public boolean canDismantle(IBlockState block, World world, BlockPos pos, EntityLivingBase wrenchedBy) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, BlockPos pos, boolean returnDrops) {
        dismantleBlock(world.getBlockState(pos), world, pos, player);
        return null;
    }

    @Override
    public boolean canDismantle(EntityPlayer player, World world, BlockPos pos) {
        return canDismantle(world.getBlockState(pos), world, pos, player);
    }

}
