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

package tbsc.techy.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.Techy;
import tbsc.techy.machine.crusher.ContainerCrusher;
import tbsc.techy.machine.crusher.GuiCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.ContainerPoweredFurnace;
import tbsc.techy.machine.furnace.GuiPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.machine.generator.ContainerGeneratorBase;
import tbsc.techy.machine.generator.GuiGeneratorBase;
import tbsc.techy.machine.generator.TileGeneratorBase;
import tbsc.techy.tile.TileMachineBase;

/**
 * Gui handler for Techy's GUIs.
 * What it does is depending on the ID given, return the correct GUI to open.
 *
 * Created by tbsc on 3/29/16.
 */
public class TechyGuiHandler implements IGuiHandler {

    /**
     * Returns a container based on GUI id
     * @param ID ID for the GUI
     * @param player the player who opened the GUI
     * @param world the world it happened in
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @return Container relevant to the GUI
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new ContainerPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new ContainerCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.COAL_GENERATOR_GUI_ID) {
            return new ContainerGeneratorBase(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    /**
     * Returns a GUI based on GUI id
     * @param ID ID for the GUI
     * @param player the player who opened the GUI
     * @param world the world it happened in
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @return GUI
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new GuiPoweredFurnace(new ContainerPoweredFurnace(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z))), (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new GuiCrusher(new ContainerCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z))), (TileCrusher) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.COAL_GENERATOR_GUI_ID) {
            return new GuiGeneratorBase(new ContainerGeneratorBase(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z))), (TileGeneratorBase) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

}
