package tbsc.techy.proxy;

import net.minecraftforge.fml.common.event.*;
import tbsc.techy.misc.cmd.CommandRetroGen;

/**
 * Server proxy, stuff will run only on server
 *
 * Created by tbsc on 3/27/16.
 */
public class ServerProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public void serverLoad(FMLServerStartingEvent event) {
        super.serverLoad(event);
        event.registerServerCommand(new CommandRetroGen());
    }

    @Override
    public void imcMessageReceived(FMLInterModComms.IMCEvent event) {
        super.imcMessageReceived(event);
    }

}
