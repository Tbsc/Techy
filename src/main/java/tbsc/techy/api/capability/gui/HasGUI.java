package tbsc.techy.api.capability.gui;

/**
 * Default implementation of the GUI capability.
 * Used to allow for showing GUIs automatically based on an item's or block's capabilities.
 *
 * Created by tbsc on 10/07/2016.
 */
public class HasGUI implements IHasGUI, IHasGUI.Modifiable {

    /**
     * The GUI ID. Stored as a public field to allow the block/item to change it.
     */
    public int guiID;

    /**
     * Default constructor, defualts to GUI ID 0.
     */
    public HasGUI() { this.guiID = 0; }

    /**
     * Constructor that allows you to specify the GUI ID.
     * @param guiID The GUI ID
     */
    public HasGUI(int guiID) {
        this.guiID = guiID;
    }

    /**
     * Returns the GUI ID, as specified by the interface's methods.
     * @return GUI ID
     */
    @Override
    public int getGUIID() {
        return guiID;
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
