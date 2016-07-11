package tbsc.techy.api.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import tbsc.techy.api.capability.dismantle.ITechyDismantleable;
import tbsc.techy.api.capability.dismantle.TechyDismantleable;
import tbsc.techy.api.capability.gui.HasGUI;
import tbsc.techy.api.capability.gui.IHasGUI;
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

    @CapabilityInject(IHasGUI.class)
    public static Capability<IHasGUI> CAPABILITY_GUI = null;

    @CapabilityInject(ITechyWrench.class)
    public static Capability<ITechyWrench> CAPABILITY_WRENCH = null;

    @CapabilityInject(ITechyDismantleable.class)
    public static Capability<ITechyDismantleable> CAPABILITY_DISMANTLEABLE = null;

    public static void register() {
        // GUI capability
        CapabilityManager.INSTANCE.register(IHasGUI.class, new Capability.IStorage<IHasGUI>() {

            @Override
            public NBTBase writeNBT(Capability<IHasGUI> capability, IHasGUI instance, EnumFacing side) {
                return new NBTTagInt(instance.getGUIID());
            }

            @Override
            public void readNBT(Capability<IHasGUI> capability, IHasGUI instance, EnumFacing side, NBTBase nbt) {
                if (instance instanceof IHasGUI.Modifiable)
                    ((IHasGUI.Modifiable) instance).setGUIID(((NBTTagInt) nbt).getInt()); // Lots of casting, eh?
            }

        }, HasGUI::new);
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
