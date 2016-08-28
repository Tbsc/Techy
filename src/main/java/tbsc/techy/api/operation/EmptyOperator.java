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

package tbsc.techy.api.operation;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Empty operator. Used by the capability to define a default, that does nothing.
 * I do this simply because the default requires a tile entity, and I can't have one.
 * Instead I created a new class that will just do nothing.
 *
 * Created by tbsc on 8/28/16.
 */
public class EmptyOperator implements IOperator {

    @Override
    public boolean updateOperation() {
        return false;
    }

    @Override
    public boolean operate() {
        return false;
    }

    @Override
    public boolean canOperate() {
        return false;
    }

    @Override
    public boolean shouldOperate() {
        return false;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getCurrentTotalProgress() {
        return 0;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

}
