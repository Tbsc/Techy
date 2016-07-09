package tbsc.techy.api.iface;

import tbsc.techy.common.loader.ObjectLoader;

/**
 * To be implemented on blocks/items, and that lets the base class know it has a GUI and
 * what GUI to open. Note that this is separate from the {@link ObjectLoader}, and therefore
 * is in a separate package.
 *
 * Created by tbsc on 09/07/2016.
 */
public interface IHasGUI {

    /**
     * Returns the GUI id to open to gui with. Used to abstractly open GUIs.
     * @return ID of the gui
     */
    int getGUIID();

}
