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

package tbsc.techy.api.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import tbsc.techy.api.capability.dismantle.ITechyDismantleable;
import tbsc.techy.api.capability.dismantle.TechyDismantleable;
import tbsc.techy.api.capability.wrench.ITechyWrench;
import tbsc.techy.api.capability.wrench.TechyWrench;
import tbsc.techy.common.loader.ObjectLoader;

/**
 * To be implemented on blocks/items, and that lets the base class know it has a GUI and
 * what GUI to open. Note that this is separate from the {@link ObjectLoader}, and therefore
 * is in a separate package.
 *
 * Created by tbsc on 09/07/2016.
 */
public class TechyCapabilities {

    @CapabilityInject(ITechyWrench.class)
    public static Capability<ITechyWrench> CAPABILITY_WRENCH = null;

    @CapabilityInject(ITechyDismantleable.class)
    public static Capability<ITechyDismantleable> CAPABILITY_DISMANTLEABLE = null;

    public static void register() {
        // Wrench capability
        CapabilityManager.INSTANCE.register(ITechyWrench.class, new Capability.IStorage<ITechyWrench>() {

            @Override
            public NBTBase writeNBT(Capability<ITechyWrench> capability, ITechyWrench instance, EnumFacing side) {
                return null; // No NBT data
            }

            @Override
            public void readNBT(Capability<ITechyWrench> capability, ITechyWrench instance, EnumFacing side, NBTBase nbt) {
                // No NBT data
            }

        }, TechyWrench::new);
        // Dismantleable capability
        CapabilityManager.INSTANCE.register(ITechyDismantleable.class, new Capability.IStorage<ITechyDismantleable>() {

            @Override
            public NBTBase writeNBT(Capability<ITechyDismantleable> capability, ITechyDismantleable instance, EnumFacing side) {
                return null; // No NBT data
            }

            @Override
            public void readNBT(Capability<ITechyDismantleable> capability, ITechyDismantleable instance, EnumFacing side, NBTBase nbt) {
                // No NBT data
            }

        }, TechyDismantleable::new);
    }

}
