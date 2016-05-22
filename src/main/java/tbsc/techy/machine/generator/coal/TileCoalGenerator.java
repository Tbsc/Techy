package tbsc.techy.machine.generator.coal;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import tbsc.techy.init.BlockInit;
import tbsc.techy.machine.generator.TileGeneratorBase;

/**
 * Implementation of {@link TileGeneratorBase} for coal generation
 *
 * Created by tbsc on 5/20/16.
 */
public class TileCoalGenerator extends TileGeneratorBase implements IEnergyProvider {

    public TileCoalGenerator() {
        super(128000, 80, BlockInit.blockCoalGenerator.tileInvSize, 0); // cookTime of zero since it's not used
    }

    @Override
    public boolean canGenerateFromItem(ItemStack item) {
        return item.getItem() == Items.COAL;
    }

    @Override
    protected int getBurnTimeFromItem(ItemStack item) {
        return 1600;
    }

    @Override
    public int getEnergyUsage(ItemStack input) {
        return 80;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        markDirty();
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

}
