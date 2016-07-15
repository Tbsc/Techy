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
