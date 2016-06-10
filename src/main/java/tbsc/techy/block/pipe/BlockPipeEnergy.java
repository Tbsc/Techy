package tbsc.techy.block.pipe;

import cofh.api.energy.IEnergyConnection;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.tile.pipe.TilePipeEnergy;

import java.util.List;

/**
 * Block for the energy pipe
 *
 * Created by tbsc on 5/9/16.
 */
public class BlockPipeEnergy extends BlockPipeBase {

    protected int capacity;
    protected int maxTransfer;

    public BlockPipeEnergy(String registryName, int capacity, int maxTransfer) {
        super(registryName, 0, BlockPipeEnergy.class);
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
    }

    @Override
    public boolean canConnectOnSide(IBlockAccess world, BlockPos thisBlock, EnumFacing side) {
        return canConnectWithBlock(world, thisBlock.offset(side));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("Capacity: " + capacity + " RF");
        tooltip.add("Max transfer: " + maxTransfer + " RF/t");
    }

    @Override
    public boolean canConnectWithBlock(IBlockAccess world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        TileEntity tile = world.getTileEntity(blockPos);
        return (tile != null && tile instanceof IEnergyConnection) || block instanceof BlockPipeEnergy; // If the block can connect to energy stuff, then we can connect to it
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeEnergy(capacity, maxTransfer);
    }

}
