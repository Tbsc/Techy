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

package tbsc.techy.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.wrench.ITechyWrench;
import tbsc.techy.api.wrench.TechyWrench;
import tbsc.techy.common.loader.annotation.Register;

import javax.annotation.Nullable;

/**
 * Class for item wrench.
 * Implements the {@link ITechyWrench} interface for compatibility.
 *
 * Created by tbsc on 10/07/2016.
 */
public class ItemWrench extends ItemTechyBase {

    @Register.Instance
    public static ItemWrench instance = new ItemWrench();

    /**
     * Constructor for the item wrench.
     */
    public ItemWrench() {
        super("itemWrench");
    }

    /**
     * Adds support for Techy's wrench API using capabilities
     * @param stack Item stack
     * @param nbt NBT data
     * @return Capabilities
     */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ICapabilityProvider() {

            @Override
            public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                return capability == TechyCapabilities.CAPABILITY_WRENCH;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
                return capability == TechyCapabilities.CAPABILITY_WRENCH ? (T) new TechyWrench() : null;
            }

        };
    }

}
