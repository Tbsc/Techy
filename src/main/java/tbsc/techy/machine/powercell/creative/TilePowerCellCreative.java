package tbsc.techy.machine.powercell.creative;

import net.minecraft.util.ResourceLocation;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.machine.powercell.TilePowerCellBase;

/**
 * Tile entity class for the creative power cell
 *
 * Created by tbsc on 6/6/16.
 */
public class TilePowerCellCreative extends TilePowerCellBase {

    public TilePowerCellCreative() { super(-1, Integer.MAX_VALUE, 1, "blockPowerCellCreative"); }

    public TilePowerCellCreative(int invSize, String registryName) {
        super(-1, Integer.MAX_VALUE, invSize, registryName);
        setConfigurationForSide(Sides.DOWN, SideConfiguration.INPUT);
    }

    @Override
    public ResourceLocation getGUITexture() {
        return new ResourceLocation("Techy:textures/gui/container/guiCreativePowerCell.png");
    }

    @Override
    public int[] getInputEnergySlots() {
        return new int[0];
    }

    @Override
    public int[] getOutputEnergySlots() {
        return new int[] {
                0
        };
    }

}
