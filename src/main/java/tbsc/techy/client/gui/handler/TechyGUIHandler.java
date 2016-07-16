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

package tbsc.techy.client.gui.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.api.iface.IHasGUI;

import java.util.HashMap;

/**
 * Techy's GUI handler. Using a registry system for registering GUIs to the handler,
 * it returns a different GUI/container from those that have been registered to the game,
 * based on the ID they have been registered with.
 *
 * Created by tbsc on 11/07/2016.
 */
public enum TechyGUIHandler implements IGuiHandler {

    /**
     * This class is an enum, to allow for creating an instance just by defining a type.
     */
    INSTANCE;

    /**
     * Registry map. Contains information about Techy's GUIs, like what GUI will be shown
     * when opening some ID.
     */
    private final HashMap<Integer, IHasGUI> registryMap = new HashMap<>();

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return registryMap.get(ID).getContainer(player, world, new BlockPos(x, y, z));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return registryMap.get(ID).getGUI(player, world, new BlockPos(x, y, z));
    }

    public void registerGUI(IHasGUI gui) {
        registryMap.put(gui.getGUIID(), gui);
    }

}
