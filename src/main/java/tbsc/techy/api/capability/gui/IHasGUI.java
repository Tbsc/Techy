package tbsc.techy.api.capability.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * To be implemented on blocks/items using capabilities, and that lets the base class know
 * it has a GUI andwhat GUI to open.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface IHasGUI {

    /**
     * Returns the GUI id to open to gui with. Used to abstractly open GUIs.
     * @return ID of the gui
     */
    int getGUIID();

    /**
     * Returns an instance of the server side element of GUIs, containers.
     * Used to register the container to the game automatically.
     * @param player The player that opened the GUI
     * @param world The world it happened in
     * @param pos The position of what opened the GUI (not always the player)
     * @return Instance of the container, based on parameters.
     */
    Container getContainer(EntityPlayer player, World world, BlockPos pos);

    /**
     * Returns the GUI that will be displayed to the user.
     * Used to register the GUI to the game automatically.
     * @param player The player that opened the GUI
     * @param world The world it happened in
     * @param pos The position of what opened the GUI (not always the player)
     * @return Instance of the GUI, based on parameters.
     */
    GuiScreen getGUI(EntityPlayer player, World world, BlockPos pos);

    interface Modifiable {

        /**
         * Sets the GUI ID.
         * @param guiID The GUI ID to set
         */
        void setGUIID(int guiID);

    }

}

