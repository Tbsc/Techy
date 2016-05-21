package tbsc.techy.item;

import cofh.lib.util.helpers.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import tbsc.techy.api.IBoosterItem;

import java.util.List;

/**
 * Base class for booster items.
 * NOTE: The metadata value of the booster value should equal to the tier.
 *
 * Created by tbsc on 5/2/16.
 */
public abstract class ItemBoosterBase extends ItemBase implements IBoosterItem {

    protected ItemBoosterBase(String unlocalizedName) {
        super(unlocalizedName);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add("Tier " + stack.getMetadata());

        tooltip.add(TextFormatting.BOLD + "***NOT WORKING ATM***"); // Todo fix boosters

        if (StringHelper.isShiftKeyDown()) {
            tooltip.add("Energy modifier: " + getEnergyModifier(stack.getMetadata()) + "%");
            tooltip.add("Time modifier: " + getTimeModifier(stack.getMetadata()) + "%");
            tooltip.add("Experience modifier: " + getExperienceModifier(stack.getMetadata()) + "%");
            tooltip.add("Additional items modifier: " + getAdditionalItemModifier(stack.getMetadata()) + " max");
        } else {
            tooltip.add(TextFormatting.ITALIC + "Shift" + TextFormatting.RESET + " for more info");
        }
    }

}
