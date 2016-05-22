package tbsc.techy.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.pipe.render.PipeEnergyBakedModel;
import tbsc.techy.block.pipe.render.PipeEnergyLoader;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;

/**
 * Anything run here will work only client-side
 *
 * Created by tbsc on 3/27/16.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    public void preInit() {
        FMLInterModComms.sendMessage("Waila", "register", "tbsc.techy.api.compat.waila.TechyWAILAPlugin.callbackRegister");

        ModelLoaderRegistry.registerLoader(new PipeEnergyLoader());
    }

    /**
     * Loads models
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void preInitClient() {
        ItemInit.initModels();
        BlockInit.initModels();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initClient() {
        BlockInit.initItemModels();
        Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(PipeEnergyBakedModel.BAKED_MODEL);
    }

}
