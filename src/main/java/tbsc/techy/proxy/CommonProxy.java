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

package tbsc.techy.proxy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import tbsc.techy.Techy;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;
import tbsc.techy.misc.cmd.CommandRetroGen;
import tbsc.techy.network.SPacketSideConfigUpdate;
import tbsc.techy.recipe.IMCRecipeHandler;

/**
 * Common proxy, stuff will run on both sides
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class CommonProxy implements IProxy {

    int packetId = 0;

    private int nextID() {
        return packetId++;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        MiscInit.preInit();
        Techy.network = NetworkRegistry.INSTANCE.newSimpleChannel("Techy");
        Techy.network.registerMessage(SPacketSideConfigUpdate.Handler.class, SPacketSideConfigUpdate.class, nextID(), Side.SERVER);
        Techy.config = new Configuration(event.getSuggestedConfigurationFile());
        Techy.syncConfig();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        MiscInit.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        MiscInit.postInit();
    }

    @Override
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRetroGen());
    }

    @Override
    public void imcMessageReceived(FMLInterModComms.IMCEvent event) {
        IMCRecipeHandler.imcMessageReceived(event.getMessages());
    }

}
