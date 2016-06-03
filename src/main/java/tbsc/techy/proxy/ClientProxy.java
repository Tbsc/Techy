package tbsc.techy.proxy;

import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;

/**
 * Anything run here will work only client-side
 *
 * Created by tbsc on 3/27/16.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        FMLInterModComms.sendMessage("Waila", "register", "tbsc.techy.api.compat.waila.TechyWAILAPlugin.callbackRegister");
        ItemInit.initModels();
        BlockInit.initModels();
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
    }

    @Override
    public void imcMessageReceived(FMLInterModComms.IMCEvent event) {
        super.imcMessageReceived(event);
    }

}
