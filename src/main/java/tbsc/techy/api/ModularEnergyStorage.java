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

package tbsc.techy.api;

import cofh.api.energy.EnergyStorage;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;

/**
 * {@link EnergyStorage} implementation that adds support for storing Tesla power.
 * Works by combining the storage of energy to the same class, and updates each energy system
 * if a change happens in any of those systems.
 *
 * Created by tbsc on 6/30/16.
 */
public class ModularEnergyStorage extends EnergyStorage implements ITeslaHolder, ITeslaProducer, ITeslaConsumer {

    public ModularEnergyStorage(int capacity) {
        super(capacity);
    }

    public ModularEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public ModularEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public long getStoredPower() {
        return getEnergyStored();
    }

    @Override
    public long getCapacity() {
        return getMaxEnergyStored();
    }

    @Override
    public long givePower(long power, boolean simulated) {
        return receiveEnergy((int) power, simulated);
    }

    @Override
    public long takePower(long power, boolean simulated) {
        return extractEnergy((int) power, simulated);
    }

}
