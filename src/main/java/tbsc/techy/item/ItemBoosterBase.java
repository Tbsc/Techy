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
