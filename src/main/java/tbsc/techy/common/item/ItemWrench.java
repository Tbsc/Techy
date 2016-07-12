package tbsc.techy.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.capability.wrench.ITechyWrench;
import tbsc.techy.api.capability.wrench.TechyWrench;
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
