package tbsc.techy.block.pipe.render;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import tbsc.techy.Techy;

/**
 * Loads the model for the energy pipe
 *
 * Created by tbsc on 5/21/16.
 */
public class PipeEnergyLoader implements ICustomModelLoader {

    public static final PipeEnergyModel ENERGY_PIPE_MODEL = new PipeEnergyModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(Techy.MODID) && "blockPipeEnergy".equals(modelLocation.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return ENERGY_PIPE_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

}
