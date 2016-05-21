package tbsc.techy.event;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tbsc.techy.Techy;

/**
 * Event handler of the mod, ATM only updates config if changes
 *
 * Created by tbsc on 3/26/16.
 */
public class GeneralEventHandler {

    /**
     * Listens to the {@link net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent} and
     * updates config if it changes.
     * @param event instance of the event posted
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Techy.MODID.equals(event.getModID())) {
            Techy.instance.syncConfig();
        }
    }

}
