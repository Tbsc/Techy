package tbsc.techy.machine.powercell;

import net.minecraft.util.ResourceLocation;

/**
 * Implementation for the basic power cell
 *
 * Created by tbsc on 6/3/16.
 */
public class TilePowerCellAdvanced extends TilePowerCellBase {

    public TilePowerCellAdvanced(int capacity, int maxTransfer, int invSize, String registryName) {
        super(capacity, maxTransfer, invSize, registryName);
    }

    @Override
    public ResourceLocation getGUITexture() {
        return new ResourceLocation("Techy:textures/gui/container/guiPowerCell.png");
    }

    @Override
    public int[] getInputEnergySlots() {
        return new int[] {
                0
        };
    }

    @Override
    public int[] getOutputEnergySlots() {
        return new int[] {
                1
        };
    }

}
