/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.machine.generator.coal;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import tbsc.techy.ConfigData;
import tbsc.techy.init.BlockInit;
import tbsc.techy.machine.generator.TileGeneratorBase;

import javax.annotation.Nonnull;

/**
 * Implementation of {@link TileGeneratorBase} for coal generation
 *
 * Created by tbsc on 5/20/16.
 */
public class TileCoalGenerator extends TileGeneratorBase implements IEnergyProvider {

    public TileCoalGenerator() {
        super(128000, 80, BlockInit.blockCoalGenerator.tileInvSize, 0, 1); // cookTime of zero since it's not used
    }

    @Override
    public boolean canGenerateFromItem(ItemStack item) {
        return item.getItem() == Items.COAL;
    }

    @Override
    protected int getBurnTimeFromItem(ItemStack item) {
        return ConfigData.coalGeneratorProcessTime;
    }

    @Override
    public int getEnergyUsage(ItemStack input) {
        return ConfigData.coalGeneratorRFPerTick;
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        markDirty();
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Nonnull
    @Override
    public int[] getEnergySlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[] {
                0
        };
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[] {
                1, 2, 3, 4
        };
    }

    @Override
    public String getName() {
        return BlockInit.blockCoalGenerator.getLocalizedName();
    }

}
