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

package tbsc.techy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.item.ItemWrench;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.network.CPacketUpdateConfig;
import tbsc.techy.proxy.IProxy;

/**
 * Main mod class.
 * Most of the data is here (such as modid).
 */
@Mod(modid = Techy.MODID, version = Techy.VERSION, dependencies = Techy.DEPENDENCIES)
public class Techy {

    /**
     * Basic data
     */
    public static final String MODID = "Techy";
    public static final String VERSION = "1.9.4-1.1.0";
    public static final String DEPENDENCIES = "required-after:Forge@[" // (taken from AE2) require forge
            + net.minecraftforge.common.ForgeVersion.majorVersion + '.' // majorVersion
            + net.minecraftforge.common.ForgeVersion.minorVersion + '.' // minorVersion
            + net.minecraftforge.common.ForgeVersion.revisionVersion + '.' // revisionVersion
            + net.minecraftforge.common.ForgeVersion.buildVersion + ",)"; // buildVersion
    public static final String CLIENT_PROXY = "tbsc.techy.proxy.ClientProxy";
    public static final String SERVER_PROXY = "tbsc.techy.proxy.ServerProxy";

    /**
     * GUI stuff
     */
    public static final int POWERED_FURNACE_GUI_ID = 0;
    public static final int CRUSHER_GUI_ID = 1;
    public static final int COAL_GENERATOR_GUI_ID = 2;
    public static final int POWER_CELL_GUI_ID = 3;
    public static final int INTERACTOR_BASIC_GUI_ID = 4;
    public static final int INTERACTOR_IMPROVED_GUI_ID = 5;
    public static final int INTERACTOR_ADVANCED_GUI_ID = 6;

    /**
     * Instance of this class
     */
    @Mod.Instance(MODID)
    public static Techy instance;

    /**
     * Network wrapper, used to send and register packets.
     */
    public static SimpleNetworkWrapper network;

    /**
     * Proxy
     */
    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    /**
     * Configuration
     */
    public static Configuration config;

    /**
     * Creative tab for items
     */
    public static CreativeTabs tabTechyItems = new CreativeTabs("techy.items") {
        @Override
        public Item getTabIconItem() {
            return ItemWrench.instance;
        }
    };

    /**
     * Creative tab for machines
     */
    public static CreativeTabs tabTechyBlocks = new CreativeTabs("techy.blocks") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockPoweredFurnace.instance);
        }
    };

    /**
     * PreInit, gets called on pre init stage of loading and registring items, blocks, tile entities
     * and config should be done here.
     * @param event The event that gets posted on preInit stage
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    /**
     * Init stage, used to add stuff to game
     * @param event
     */
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * Gets called when the server is loaded. On a server this is when it's started, on client
     * is when the integrated server is started (world load)
     * @param event
     */
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        proxy.serverLoad(event);
    }

    /**
     * Post init stage, ATM used to load vanilla furnace recipes because on post init
     * stage I can be sure that all mods have added their recipes and that I can put them on my
     * own furnace.
     * @param event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    /**
     * When an IMC message is received, let the handler do its thing with the messages
     * @param event IMC event instance
     */
    @EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event) {
        proxy.imcMessageReceived(event);
    }

    /**
     * When a player joins, send the config to him.
     * Since the event doesn't give me the player that joined (?), I just send it
     * to all players.
     * Only called on the server.
     */
    @SideOnly(Side.SERVER)
    @SubscribeEvent
    public void onServerJoin(FMLNetworkEvent.ServerConnectionFromClientEvent event) {
        if (!event.isLocal()) {
            Techy.network.sendToAll(new CPacketUpdateConfig());
        }
    }

    /**
     * When the player finally disconnects, return to default values instead of the values
     * the server sent. This is only called on client side.
     * @param event
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Techy.syncConfig();
    }

    /**
     * Syncs saved config with the mod.
     */
    public static void syncConfig() {
        try {
            // Load config
            config.load();

            // Config properties //

            // Furnace
            ConfigData.furnaceDefaultCookTime = config.get("Powered Furnace", "DefaultCookTime", ConfigData.furnaceDefaultCookTime,
                    "Define (in ticks) the processing time for vanilla recipes in the powered furnace.", 1, Integer.MAX_VALUE).getInt();
            ConfigData.furnaceDefaultEnergyUsage = config.get("Powered Furnace", "DefaultEnergyUsage", ConfigData.furnaceDefaultEnergyUsage,
                    "Amount of energy consumed when vanilla recipes are processed in the powered furnace", 0, Integer.MAX_VALUE).getInt();

            // Crusher
            ConfigData.crusherDefaultProcessTime = config.get("Crusher", "DefaultCookTime", ConfigData.crusherDefaultProcessTime,
                    "The time in ticks it takes to complete a process in the crusher.", 1, Integer.MAX_VALUE).getInt();
            ConfigData.crusherDefaultEnergyUsage = config.get("Crusher", "DefaultEnergyUsage", ConfigData.crusherDefaultEnergyUsage,
                    "How much energy is used when doing a default recipe.", 0, Integer.MAX_VALUE).getInt();

            // Coal Generator
            ConfigData.coalGeneratorProcessTime = config.get("CoalGenerator", "DefaultBurnTime", ConfigData.coalGeneratorProcessTime,
                    "Default time for a piece of coal to burn in a coal generator").getInt();
            ConfigData.coalGeneratorRFPerTick = config.get("CoalGenerator", "RFPerTick", ConfigData.coalGeneratorRFPerTick,
                    "How much energy is generated per tick in the coal generator").getInt();

            // Basic power cell
            ConfigData.basicPowerCellCapacity = config.get("BasicPowerCell", "Capacity", ConfigData.basicPowerCellCapacity,
                    "Maximum amount of RF a basic power cell can hold").getInt();
            ConfigData.basicPowerCellTransferRate = config.get("BasicPowerCell", "MaxTransferRate", ConfigData.basicPowerCellTransferRate,
                    "Maximum amount of RF a basic power cell can transfer per tick").getInt();

            // Improved power cell
            ConfigData.improvedPowerCellCapacity = config.get("ImprovedPowerCell", "Capacity", ConfigData.improvedPowerCellCapacity,
                    "Maximum amount of RF an improved power cell can hold").getInt();
            ConfigData.improvedPowerCellTransferRate = config.get("ImprovedPowerCell", "MaxTransferRate", ConfigData.improvedPowerCellTransferRate,
                    "Maximum amount of RF an improved power cell can transfer per tick").getInt();

            // Advanced power cell
            ConfigData.advancedPowerCellCapacity = config.get("AdvancedPowerCell", "Capacity", ConfigData.advancedPowerCellCapacity,
                    "Maximum amount of RF an advanced power cell can hold").getInt();
            ConfigData.advancedPowerCellTransferRate = config.get("AdvancedPowerCell", "MaxTransferRate", ConfigData.advancedPowerCellTransferRate,
                    "Maximum amount of RF an advanced power cell can transfer per tick").getInt();

            // World Gen - settings
            ConfigData.shouldGenerateCopper = config.get("WorldGen", "ShouldGenerateCopper", ConfigData.shouldGenerateCopper,
                    "Should Techy generate copper in the world").getBoolean();
            ConfigData.shouldGenerateTin = config.get("WorldGen", "ShouldGenerateTin", ConfigData.shouldGenerateTin,
                    "Should Techy generate tin in the world").getBoolean();
            ConfigData.shouldGenerateSilver = config.get("WorldGen", "ShouldGenerateSilver", ConfigData.shouldGenerateSilver,
                    "Should Techy generate silver in the world").getBoolean();
            ConfigData.shouldGenerateAluminium = config.get("WorldGen", "ShouldGenerateAluminium", ConfigData.shouldGenerateAluminium,
                    "Should Techy generate aluminium in the world").getBoolean();
            ConfigData.shouldGenerateLithium = config.get("WorldGen", "ShouldGenerateLithium", ConfigData.shouldGenerateLithium,
                    "Should Techy generate lithium in the world").getBoolean();
            
            // World Gen - copper
            ConfigData.copperPerChunk = config.get("WorldGen", "CopperPerChunk", ConfigData.copperPerChunk,
                    "How much copper ores should be generated in each chunk (16x256x16)").getInt();
            ConfigData.copperMaxHeight = config.get("WorldGen", "CopperMaxHeight", ConfigData.copperMaxHeight,
                    "The maximum height copper ores can be generated in").getInt();
            ConfigData.copperPerVein = config.get("WorldGen", "CopperPerVein", ConfigData.copperPerVein,
                    "Maximum amount of copper per vein generated").getInt();

            // World Gen - tin
            ConfigData.tinPerChunk = config.get("WorldGen", "TinPerChunk", ConfigData.tinPerChunk,
                    "How much tin ores should be generated in each chunk (16x256x16)").getInt();
            ConfigData.tinMaxHeight = config.get("WorldGen", "TinMaxHeight", ConfigData.tinMaxHeight,
                    "The maximum height tin ores can be generated in").getInt();
            ConfigData.tinPerVein = config.get("WorldGen", "TinPerVein", ConfigData.tinPerVein,
                    "Maximum amount of tin per vein generated").getInt();

            // World Gen - silver
            ConfigData.silverPerChunk = config.get("WorldGen", "SilverPerChunk", ConfigData.silverPerChunk,
                    "How much silver ores should be generated in each chunk (16x256x16)").getInt();
            ConfigData.silverMaxHeight = config.get("WorldGen", "SilverMaxHeight", ConfigData.silverMaxHeight,
                    "The maximum height silver ores can be generated in").getInt();
            ConfigData.silverPerVein = config.get("WorldGen", "SilverPerVein", ConfigData.silverPerVein,
                    "Maximum amount of silver per vein generated").getInt();

            // World Gen - aluminium
            ConfigData.aluminiumPerChunk = config.get("WorldGen", "AluminiumPerChunk", ConfigData.aluminiumPerChunk,
                    "How much aluminium ores should be generated in each chunk (16x256x16)").getInt();
            ConfigData.aluminiumMaxHeight = config.get("WorldGen", "AluminiumMaxHeight", ConfigData.aluminiumMaxHeight,
                    "The maximum height aluminium ores can be generated in").getInt();
            ConfigData.aluminiumPerVein = config.get("WorldGen", "AluminiumPerVein", ConfigData.aluminiumPerVein,
                    "Maximum amount of aluminium per vein generated").getInt();

            // World Gen - lithium
            ConfigData.lithiumPerChunk = config.get("WorldGen", "LithiumPerChunk", ConfigData.lithiumPerChunk,
                    "How much lithium ores should be generated in each chunk (16x256x16)").getInt();
            ConfigData.lithiumMaxHeight = config.get("WorldGen", "LithiumMaxHeight", ConfigData.lithiumMaxHeight,
                    "The maximum height lithium ores can be generated in").getInt();
            ConfigData.lithiumPerVein = config.get("WorldGen", "LithiumPerVein", ConfigData.lithiumPerVein,
                    "Maximum amount of lithium per vein generated").getInt();
        } catch (Exception e) {
            e.printStackTrace();
            FMLLog.warning("Techy is unable to load config!");
            // Fails loading
        } finally {
            // Save props to config
            if (config.hasChanged()) config.save();
        }
    }

}
