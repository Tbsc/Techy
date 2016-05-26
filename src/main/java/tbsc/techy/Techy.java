package tbsc.techy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;
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
    public static final String VERSION = "MC1.9.4-v2";
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
    public static CreativeTabs tabTechyMachines = new CreativeTabs("techy.machines") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockInit.blockPoweredFurnace);
        }
    };

    /**
     * PreInit, gets called on pre init stage of loading and registring items, blocks, tile entities
     * and config should be done here.
     * @param event The event that gets posted on preInit stage
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        MiscInit.init();
        proxy.preInitClient();
        proxy.preInit();

        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initClient();
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

            // Config properties
            ConfigData.furnaceDefaultCookTime = config.get("Powered Furnace", "DefaultCookTime", ConfigData.furnaceDefaultCookTime,
                    "Define (in ticks) the processing time for vanilla recipes in the powered furnace.", 1, Integer.MAX_VALUE).getInt();
            ConfigData.furnaceDefaultEnergyUsage = config.get("Powered Furnace", "DefaultEnergyUsage", ConfigData.furnaceDefaultEnergyUsage,
                    "Amount of energy consumed when vanilla recipes are processed in the powered furnace", 0, Integer.MAX_VALUE).getInt();
            ConfigData.crusherDefaultProceessTime = config.get("Crusher", "DefaultCookTime", ConfigData.crusherDefaultProceessTime,
                    "The time in ticks it takes to complete a process in the crusher.", 1, Integer.MAX_VALUE).getInt();
            ConfigData.crusherDefaultEnergyUsage = config.get("Crusher", "DefaultEnergyUsage", ConfigData.crusherDefaultEnergyUsage,
                    "How much energy is used when doing a default recipe.", 0, Integer.MAX_VALUE).getInt();
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
