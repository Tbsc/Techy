package tbsc.techy.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import tbsc.techy.api.TechyProps;
import tbsc.techy.common.item.ItemWrench;
import tbsc.techy.common.proxy.IProxy;

/**
 * Class for any **internal** constants for the mod. For any constants that can be used externally,
 * check the {@link TechyProps} API class.
 *
 * Created by tbsc on 22/07/2016.
 */
public class InternalProps {

    /**
     * Network wrapper, used to send and register packets.
     */
    public static SimpleNetworkWrapper NETWORK;

    /**
     * Proxy instance, server/client proxies get injected to this field based on game side
     */
    @SidedProxy(clientSide = TechyProps.CLIENT_PROXY, serverSide = TechyProps.SERVER_PROXY)
    public static IProxy PROXY;

    /**
     * The mod's creative tab, with the wrench as its icon!
     */
    public static CreativeTabs TAB_TECHY = new CreativeTabs("techy") {

        @Override
        public Item getTabIconItem() {
            return ItemWrench.instance;
        }

    };

}
