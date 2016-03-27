package tbsc.techy.init;

import net.minecraftforge.common.MinecraftForge;
import tbsc.techy.event.GeneralEventHandler;

/**
 * Created by tbsc on 3/26/16.
 */
public class MiscInit {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
    }

}
