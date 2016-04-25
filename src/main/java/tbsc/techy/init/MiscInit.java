package tbsc.techy.init;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tbsc.techy.Techy;
import tbsc.techy.client.gui.TechyGuiHandler;
import tbsc.techy.event.GeneralEventHandler;
import tbsc.techy.recipe.PoweredFurnaceRecipes;

/**
 * Anything that isn't a block/tile/item and that it needs to get loaded on startup goes here.
 *
 * Created by tbsc on 3/26/16.
 */
public class MiscInit {

    /**
     * Loads everything needed to be run. (Such as events and GUI handlers)
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Techy.instance, new TechyGuiHandler());
    }

    /**
     * Everything that needs to be run late.
     */
    public static void postInit() {
        PoweredFurnaceRecipes.init();
    }

}
