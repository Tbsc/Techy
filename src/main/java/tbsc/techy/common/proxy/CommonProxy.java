package tbsc.techy.common.proxy;

import net.minecraftforge.fml.common.event.*;

/**
 * Common proxy. Everything done here will be called on both sides of the game.
 *
 * Created by tbsc on 09/07/2016.
 */
public abstract class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void serverLoad(FMLServerStartingEvent event) {

    }

    @Override
    public void imcMessageReceived(FMLInterModComms.IMCEvent event) {

    }

}
