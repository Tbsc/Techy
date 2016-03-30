package tbsc.techy.init;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tbsc.techy.Techy;
import tbsc.techy.client.gui.TechyGuiHandler;
import tbsc.techy.event.GeneralEventHandler;

/**
 * Created by tbsc on 3/26/16.
 */
public class MiscInit {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Techy.instance, new TechyGuiHandler());
    }

}
