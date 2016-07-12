package tbsc.techy.api.capability.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Default implementation of the GUI capability.
 * Used to allow for showing GUIs automatically based on an item's or block's capabilities.
 *
 * Created by tbsc on 10/07/2016.
 */
public class HasGUI<G extends GuiScreen, C extends Container> implements IHasGUI, IHasGUI.Modifiable {

    /**
     * The GUI ID. Stored as a public field to allow the block/item to change it.
     */
    public int guiID;
    public C container;
    public G gui;

    /**
     * Default constructor, defualts to GUI ID 0.
     */
    public HasGUI() { this.guiID = 0; }

    /**
     * Constructor that allows you to specify the GUI ID.
     * @param guiID The GUI ID
     */
    public HasGUI(int guiID, C container, G gui) {
        this.guiID = guiID;
        this.container = container;
        this.gui = gui;
    }

    /**
     * Returns the GUI ID, as specified by the interface's methods.
     * @return GUI ID
     */
    @Override
    public int getGUIID() {
        return guiID;
    }

    @Override
    public Container getContainer(EntityPlayer player, World world, BlockPos pos) {
        return container;
    }

    @Override
    public GuiScreen getGUI(EntityPlayer player, World world, BlockPos pos) {
        return gui;
    }

    /**
     * Sets the GUI ID.
     * @param guiID The GUI ID to set
     */
    @Override
    public void setGUIID(int guiID) {
        this.guiID = guiID;
    }

}
