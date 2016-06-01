package tbsc.techy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;
import tbsc.techy.misc.cmd.CommandRetroGen;
import tbsc.techy.network.SPacketSideConfigUpdate;
import tbsc.techy.proxy.IProxy;
import tbsc.techy.recipe.IMCRecipeHandler;

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
    public static final String VERSION = "MC1.9.4-v3";
    public static final String DEPENDENCIES = "required-after:Forge@[12.17.0.1909,)";
    public static final String CLIENT_PROXY = "tbsc.techy.proxy.ClientProxy";
    public static final String SERVER_PROXY = "tbsc.techy.proxy.ServerProxy";

    /**
     * GUI stuff
     */
    public static final int POWERED_FURNACE_GUI_ID = 0;
    public static final int CRUSHER_GUI_ID = 1;
    public static final int COAL_GENERATOR_GUI_ID = 2;

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
            return ItemInit.itemWrench;
        }
    };

    /**
     * Creative tab for machines
     */
    public static CreativeTabs tabTechyBlocks = new CreativeTabs("techy.blocks") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockInit.blockPoweredFurnace);
        }
    };

    int packetId = 0;

    private int nextID() {
        return packetId++;
    }

    /**
     * PreInit, gets called on pre init stage of loading and registring items, blocks, tile entities
     * and config should be done here.
     * @param event The event that gets posted on preInit stage
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        MiscInit.preInit();
        proxy.preInitClient();
        proxy.preInit();

        network = NetworkRegistry.INSTANCE.newSimpleChannel("Techy");
        network.registerMessage(SPacketSideConfigUpdate.Handler.class, SPacketSideConfigUpdate.class, nextID(), Side.SERVER);

        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initClient();
        MiscInit.init();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRetroGen());
    }

    /**
     * Post init stage, ATM used to load vanilla furnace recipes because on post init
     * stage I can be sure that all mods have added their recipes and that I can put them on my
     * own furnace.
     * @param event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        MiscInit.postInit();
    }

    /**
     * When an IMC message is received, let the handler do its thing with the messages
     * @param event IMC event instance
     */
    @EventHandler
    public void imcMessage(FMLInterModComms.IMCEvent event) {
        IMCRecipeHandler.imcMessageReceived(event.getMessages());
    }

    /**
     * Syncs saved config with the mod.
     */
    public void syncConfig() {
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
