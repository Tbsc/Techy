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

package tbsc.techy.machine.crusher;

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
import tbsc.techy.api.register.IHasTileEntity;
import tbsc.techy.api.register.RegisterInstance;
import tbsc.techy.api.register.TechyRegister;
import tbsc.techy.block.BlockBaseFacingMachine;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Crusher's block class.
 *
 * Created by tbsc on 5/4/16.
 */
public class BlockCrusher extends BlockBaseFacingMachine implements IHasTileEntity {

    public static final String IDENTIFIER = "blockCrusher";

    @RegisterInstance(identifier = IDENTIFIER, registerClass = BlockCrusher.class)
    public static BlockCrusher instance;

    public static int tileInvSize = 7;

    @TechyRegister(identifier = IDENTIFIER)
    public BlockCrusher() {
        super(Material.IRON, IDENTIFIER, tileInvSize);
        setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        setHardness(4.0F);
        setResistance(5.5F);
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
            playerIn.openGui(Techy.instance, Techy.CRUSHER_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
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

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileCrusher();
    }

    @Override
    public <T extends TileEntity> Class<T> getTileClass() {
        return (Class<T>) TileCrusher.class;
    }

    @Override
    public String getTileID() {
        return "tileCrusher";
    }

}
