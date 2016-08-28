/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

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
