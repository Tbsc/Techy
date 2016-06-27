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

package tbsc.techy.machine.furnace;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.material.Material;
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

/**
 * Powered furnace block class.
 *
 * Created by tbsc on 3/27/16.
 */
public class BlockPoweredFurnace extends BlockBaseFacingMachine {

    /**
     * Size of the inventory.
     * Slot 1 - Input
     * Slot 2 - Output
     * Slot 3 - Booster slot #1
     * Slot 4 - Booster slot #2
     * Slot 5 - Booster slot #3
     * Slot 6 - Booster slot #4
     */
    public static int tileInvSize = 6;

    public static final String IDENTIFIER = "blockPoweredFurnace";

    public BlockPoweredFurnace() {
        super(Material.IRON, IDENTIFIER, tileInvSize);
        setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        setHardness(4.0F);
        setResistance(5.5F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /**
     * When called, will return a new instance of this block's specific TileEntity.
     * @param worldIn the world it is happening in
     * @param meta the metadata of the block
     * @return New instance of {@link TilePoweredFurnace}
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePoweredFurnace();
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
            playerIn.openGui(Techy.instance, Techy.POWERED_FURNACE_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

}
