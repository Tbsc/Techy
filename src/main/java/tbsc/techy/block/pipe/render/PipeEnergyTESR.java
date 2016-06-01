package tbsc.techy.block.pipe.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import tbsc.techy.tile.pipe.TilePipeEnergy;

/**
 * Tile entity special renderer for energy pipe.
 * Used to render the flow of energy through the pipe, when it occurs.
 * The model itself is done using a custom baked model {@link PipeEnergyBakedModel}.
 *
 * Created by tbsc on 5/29/16.
 */
public class PipeEnergyTESR<T extends TilePipeEnergy> extends TileEntitySpecialRenderer {

    /**
     * Gets called every tick, and renders the energy flowing on screen ONLY.
     * @param tile Pipe to render in
     * @param x position on x coords
     * @param y position on y coords
     * @param z position on z coords
     * @param partialTicks
     * @param destroyStage stage of block destruction, if any (then 0)
     */
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks, int destroyStage) {

    }

}
