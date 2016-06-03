package tbsc.techy.machine.powercell;

import tbsc.techy.init.BlockInit;

/**
 * Implementation for the basic power cell
 *
 * Created by tbsc on 6/3/16.
 */
public class TilePowerCellBasic extends TilePowerCellBase {

    public TilePowerCellBasic(int capacity, int maxTransfer, String registryName) {
        super(capacity, maxTransfer, BlockInit.blockBasicPowerCell.tileInvSize, registryName);
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
