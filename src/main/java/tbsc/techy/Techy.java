package tbsc.techy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.init.MiscInit;

@Mod(modid = Techy.MODID, version = Techy.VERSION)
public class Techy {

    public static final String MODID = "Techy";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static Techy instance;

    public static Configuration config;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BlockInit.init();
        ItemInit.init();
        MiscInit.init();

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
