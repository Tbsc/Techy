package tbsc.techy.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;
import tbsc.techy.block.pipe.client.PipeEnergyISBM;

/**
 * Event handler for any client events
 *
 * Created by tbsc on 5/13/2016.
 */
@SideOnly(Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        Object object = event.modelRegistry.getObject(PipeEnergyISBM.modelResourceLocation);
        if (object != null) {
            PipeEnergyISBM customModel = new PipeEnergyISBM();
            event.modelRegistry.putObject(PipeEnergyISBM.modelResourceLocation, customModel);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitchEvent(TextureStitchEvent event) {
        Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(new ResourceLocation(Techy.MODID + ":blocks/pipeEnergyTexture"));
    }

}
