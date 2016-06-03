package tbsc.techy.proxy;

import net.minecraftforge.fml.common.event.*;

/**
 * Base interface for proxy
 *
 * Created by tbsc on 3/27/16.
 */
public interface IProxy {

    void init(FMLInitializationEvent event);

    void preInit(FMLPreInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void serverLoad(FMLServerStartingEvent event);

    void imcMessageReceived(FMLInterModComms.IMCEvent event);

}
