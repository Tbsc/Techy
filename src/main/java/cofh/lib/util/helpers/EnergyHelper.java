package cofh.lib.util.helpers;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import java.util.List;

/**
 * This class contains helper functions related to Redstone Flux, the basis of the CoFH Energy System.
 *
 * @author King Lemming
 *
 */
public class EnergyHelper {

	public static final int RF_PER_MJ = 10; // Official Ratio of RF to MJ
											// (BuildCraft)
	public static final int RF_PER_EU = 4; // Official Ratio of RF to EU
											// (IndustrialCraft)

	private EnergyHelper() {

	}

	/* NBT TAG HELPER */
	public static void addEnergyInformation(ItemStack stack, List<String> list) {

		if (stack.getItem() instanceof IEnergyContainerItem) {
			list.add(StringHelper.localize("info.cofh.charge") + ": " + StringHelper.getScaledNumber(stack.getTagCompound().getInteger("Energy")) + " / "
					+ StringHelper.getScaledNumber(((IEnergyContainerItem) stack.getItem()).getMaxEnergyStored(stack)) + " RF");
		}
	}

	/* IEnergyContainer Interaction */
	public static int extractEnergyFromContainer(ItemStack container, int maxExtract, boolean simulate) {

		return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).extractEnergy(container, maxExtract, simulate) : 0;
	}

	public static int insertEnergyIntoContainer(ItemStack container, int maxReceive, boolean simulate) {

		return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).receiveEnergy(container, maxReceive, simulate) : 0;
	}

	public static boolean isPlayerHoldingEnergyContainerItem(EntityPlayer player) {

		return isEnergyContainerItem(player.getHeldItem(EnumHand.MAIN_HAND)) || isEnergyContainerItem(player.getHeldItem(EnumHand.OFF_HAND));
	}

	public static boolean isEnergyContainerItem(ItemStack container) {

		return container != null && container.getItem() instanceof IEnergyContainerItem;
	}

	public static ItemStack setDefaultEnergyTag(ItemStack container, int energy) {

		if (!container.hasTagCompound()) {
			container.setTagCompound(new NBTTagCompound());
		}
		container.getTagCompound().setInteger("Energy", energy);

		return container;
	}

	public static boolean isEnergyProviderFromSide(TileEntity tile, EnumFacing from) {

		return tile instanceof IEnergyProvider && ((IEnergyProvider) tile).canConnectEnergy(from);
	}

}
