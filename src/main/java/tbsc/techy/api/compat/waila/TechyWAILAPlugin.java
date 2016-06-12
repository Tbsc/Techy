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

package tbsc.techy.api.compat.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import tbsc.techy.tile.TileMachineBase;

import java.util.List;

/**
 * WAILA Techy plugin
 *
 * Created by tbsc on 5/6/16.
 */
@Optional.Interface(iface = "mcp.mobius.waila.IWailaDataProvider", modid = "Waila")
public class TechyWAILAPlugin implements IWailaDataProvider {

    @Optional.Method(modid = "Waila")
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Optional.Method(modid = "Waila")
    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Optional.Method(modid = "Waila")
    @Override
    public List<String> getWailaBody(ItemStack stack, List<String> list, IWailaDataAccessor data, IWailaConfigHandler config) {
        TileMachineBase tile = (TileMachineBase) data.getTileEntity();
        list.clear();
        list.add(tile.getField(0) + " / " + tile.getField(4) + " RF");
        if (tile.getField(2) == 0) {
            list.add("Not Operating");
        } else {
            list.add("Operating: " + tile.getField(1) * 100 / tile.getField(2) + "%");
        }
        return list;
    }

    @Optional.Method(modid = "Waila")
    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Optional.Method(modid = "Waila")
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity, NBTTagCompound nbtTagCompound, World world, BlockPos blockPos) {
        return null;
    }

    @Optional.Method(modid = "Waila")
    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new TechyWAILAPlugin(), TileMachineBase.class);
    }

}
