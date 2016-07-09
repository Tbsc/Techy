package tbsc.techy.common.proxy;

import net.minecraftforge.fml.common.event.*;

/**
 * Proxy interface
 *
 * Created by tbsc on 09/07/2016.
 */
public interface IProxy {

    /**
     * Pre initialization game lifecycle event
     * @param event
     */
    void preInit(FMLPreInitializationEvent event);

    /**
     * Initialization game lifecycle event
     * @param event
     */
    void init(FMLInitializationEvent event);

    /**
     * Post initialization game lifecycle event
     * @param event
     */
    void postInit(FMLPostInitializationEvent event);

    /**
     * When server starts
     * @param event
     */
    void serverLoad(FMLServerStartingEvent event);

    /**
     * Method for when receiving an IMC message
     * @param event
     */
    void imcMessageReceived(FMLInterModComms.IMCEvent event);

}
