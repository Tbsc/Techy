package tbsc.techy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;
import tbsc.techy.proxy.IProxy;

@Mod(modid = Techy.MODID, version = Techy.VERSION)
public class Techy {

    public static final String MODID = "Techy";
    public static final String VERSION = "1.0.0";
    public static final String CLIENT_PROXY = "tbsc.techy.proxy.ClientProxy";
    public static final String SERVER_PROXY = "tbsc.techy.proxy.ServerProxy";

    @Mod.Instance(MODID)
    public static Techy instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    public static Configuration config;

    public static CreativeTabs tabTechyItems = new CreativeTabs("techy.items") {
        @Override
        public Item getTabIconItem() {
            return ItemInit.itemWrench;
        }
    };

    public static CreativeTabs tabTechyMachines = new CreativeTabs("techy.machines") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(BlockInit.blockPoweredFurnace);
        }
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        MiscInit.init();
        proxy.initModels();

        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    public void syncConfig() {
        try {
            // Load config
            config.load();

            // Config properties

        } catch (Exception e) {
            // Fails loading
        } finally {
            // Save props to config
            if (config.hasChanged()) config.save();
        }
    }

}
