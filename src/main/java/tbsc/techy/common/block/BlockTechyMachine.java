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

package tbsc.techy.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tbsc.techy.api.TechyProps;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.dismantle.TechyDismantleable;
import tbsc.techy.api.loader.IHasTileEntity;
import tbsc.techy.client.gui.handler.TechyGUIHandler;
import tbsc.techy.common.machine.MachineType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base class for machines. Provides stuff like
 *
 * Created by tbsc on 12/07/2016.
 */
public class BlockTechyMachine<T extends TileEntity> extends BlockTechyBase implements IHasTileEntity<T>, ICapabilityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORKING = PropertyBool.create("working");

    @Nonnull
    public final MachineType type;

    public BlockTechyMachine(MachineType machineType) {
        super(machineType.getRegistryName(), Material.IRON);
        setHardness(2.0F);
        setHarvestLevel("pickaxe", Item.ToolMaterial.WOOD.getHarvestLevel());
        this.type = machineType;

        if (type.hasGUI()) {
            TechyGUIHandler.INSTANCE.registerGUI(type.getGUI());
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (type.hasGUI() && !worldIn.isRemote) {
            playerIn.openGui(TechyProps.INSTANCE, type.getGUI().getGUIID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    //    /**
//     * Return the {@link IBlockState} of the block from metadata
//     * @param meta metadata
//     * @return {@link IBlockState} based on that metadata value
//     */
//    @Override
//    public IBlockState getStateFromMeta(int meta) {
//        return getDefaultState()
//                .withProperty(FACING, EnumFacing.getFront((meta & 3) + 2))
//                .withProperty(WORKING, (meta & 8) != 0);
//    }
//
//    /**
//     * Return metadata value from {@link IBlockState}
//     * @param state {@link IBlockState} to be checked
//     * @return the metadata for the {@link IBlockState}
//     */
//    @Override
//    public int getMetaFromState(IBlockState state) {
//        return state.getValue(FACING).getIndex() + (state.getValue(WORKING) ? 8 : 0);
//    }
//
//    /**
//     * Specifies what properties can this block state hold
//     * @return BlockStateContainer with this block's properties
//     */
//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, FACING, WORKING);
//    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> getTileClass() {
        return (Class<T>) type.getTileClass();
    }

    @Override
    public String getTileIdentifier() {
        return type.getTileIdentifier();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return type.getTile(worldIn, meta);
    }

    TechyDismantleable dismantleableCap = new TechyDismantleable();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == TechyCapabilities.CAPABILITY_DISMANTLEABLE;
    }

    @Override
    @SuppressWarnings("unchecked") // ignoring unchecked casting, i know casting is safe
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == TechyCapabilities.CAPABILITY_DISMANTLEABLE ? (T) dismantleableCap : null;
    }

}
