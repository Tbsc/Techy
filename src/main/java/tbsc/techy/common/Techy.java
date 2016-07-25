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

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import tbsc.techy.api.TechyProps;

/**
 * Main mod class.
 * Most of the data is here (such as modid).
 */
@Mod(modid = TechyProps.MODID, version = TechyProps.VERSION, dependencies = TechyProps.DEPENDENCIES)
public class Techy {

    /**
     * PreInit, gets called on pre init stage of loading and registring items, blocks, tile entities
     * and config should be done here.
     * @param event The event that gets posted on preInit stage
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        InternalProps.PROXY.preInit(event);
    }

    /**
     * Init stage, used to add stuff to game
     * @param event
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        InternalProps.PROXY.init(event);
    }

    /**
     * Gets called when the server is loaded. On a server this is when it's started, on client
     * is when the integrated server is started (world load)
     * @param event
     */
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        InternalProps.PROXY.serverLoad(event);
    }

    /**
     * Post init stage, ATM used to load vanilla furnace recipes because on post init
     * stage I can be sure that all mods have added their recipes and that I can put them on my
     * own furnace.
     * @param event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        InternalProps.PROXY.postInit(event);
    }

    /**
     * When an IMC message is received, let the handler do its thing with the messages
     * @param event IMC event instance
     */
    @EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event) {
        InternalProps.PROXY.imcMessageReceived(event);
    }

}
