package tbsc.techy.event;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tbsc.techy.Techy;

/**
 * Created by tbsc on 3/26/16.
 */
public class GeneralEventHandler {

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        Techy.instance.syncConfig();
    }

}
