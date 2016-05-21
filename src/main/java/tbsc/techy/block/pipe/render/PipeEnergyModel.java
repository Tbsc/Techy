package tbsc.techy.block.pipe.render;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import tbsc.techy.Techy;

import java.util.Collection;
import java.util.Collections;

/**
 * Model for the energy pipe
 *
 * Created by tbsc on 5/21/16.
 */
public class PipeEnergyModel implements IModel {

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new PipeEnergyBakedModel(state, format, bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(new ResourceLocation(Techy.MODID, "blocks/pipeEnergyTexture"));
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

}
