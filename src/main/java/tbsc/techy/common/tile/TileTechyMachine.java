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

package tbsc.techy.common.tile;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.operation.IOperator;

/**
 * Subclass of {@link TileTechyBase}, that adds more support for machine stuff,
 * such as processing and automation.
 * Use {@link tbsc.techy.api.operation.OperatorBuilder} to define function, and set
 * the value of {@link #operator} to the generated instance.
 *
 * Created by tbsc on 13/07/2016.
 */
public class TileTechyMachine extends TileTechyBase implements ITickable {

    protected IOperator operator;

    @Override
    public void update() {
        if (operator.updateOperation())
            this.markDirty();
    }

    public TileTechyMachine(int invSize) {
        super(invSize);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (operator != null && capability == TechyCapabilities.CAPABILITY_OPERATOR)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (operator != null && capability == TechyCapabilities.CAPABILITY_OPERATOR)
            return (T) operator;
        return super.getCapability(capability, facing);
    }

}
