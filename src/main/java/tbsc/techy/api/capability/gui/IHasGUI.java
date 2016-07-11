package tbsc.techy.api.capability.gui;

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

    interface Modifiable {

        /**
         * Sets the GUI ID.
         * @param guiID The GUI ID to set
         */
        void setGUIID(int guiID);

    }

}

