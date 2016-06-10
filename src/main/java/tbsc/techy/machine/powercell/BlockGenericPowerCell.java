package tbsc.techy.machine.powercell;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tbsc.techy.Techy;
import tbsc.techy.api.ITechyWrench;
import tbsc.techy.block.BlockBaseFacingMachine;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Generic block for adding power cells to the game.
 * This class is built so the only thing you need to do is register an instance of this
 * class, and it'll take care of everything (mostly, I can't do tile entities).
 *
 * Created by tbsc on 6/3/16.
 */
public class BlockGenericPowerCell extends BlockBaseFacingMachine {

    protected TilePowerCellBase tile;

    public BlockGenericPowerCell(String registryName, TilePowerCellBase tile) {
        super(Material.IRON, registryName, 2);
        this.tile = tile;
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        setHardness(4.0F);
        setResistance(5.5F);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("Capacity: " + tile.getCapacity() + " RF");
        tooltip.add("Max transfer: " + tile.energyStorage.getMaxExtract() + " RF/t");
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * Gets called when a player hits the block (right click).
     * @param worldIn world
     * @param pos position of the block
     * @param state the block(state)
     * @param playerIn the player that did the action
     * @param side the side that got hit
     * @param hitX the position (on the x axis) that got hit on the block
     * @param hitY the position (on the y axis) that got hit on the block
     * @param hitZ the position (on the z axis) that got hit on the block
     * @return i have no idea, darn you obfuscation
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null && heldItem.getItem() instanceof ITechyWrench) {
            return false;
        }
        if (!worldIn.isRemote) {
            playerIn.openGui(Techy.instance, Techy.POWER_CELL_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public boolean canConnectOnSide(IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (world.getTileEntity(pos.offset(side)) != null) {
            if (world.getTileEntity(pos.offset(side)) instanceof IEnergyHandler || world.getTileEntity(pos.offset(side)) instanceof IInventory) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, NORTH, SOUTH, WEST, EAST, UP, DOWN);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return tile;
    }

}
