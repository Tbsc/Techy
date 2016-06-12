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

import net.minecraft.item.ItemStack;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.tile.TileMachineBase;

/**
 * Should be implemented on items that have a boosting functionality.
 *
 * Created by tbsc on 5/1/16.
 */
public interface IBoosterItem {

    /**
     * The percentage of energy to be consumed.
     * @param tier of booster
     * @return percentage of energy to consume
     */
    int getEnergyModifier(int tier);

    /**
     * Percentage of time progress.
     * @param tier of booster
     * @return percentage of time for progress
     */
    int getTimeModifier(int tier);

    /**
     * Percentage of experience to be given.
     * @param tier of booster
     * @return experience multiplier
     */
    int getExperienceModifier(int tier);

    /**
     * Should return the amount of max possible additional items that this booster
     * will be able to make.
     * The algorithm that uses this value is explained in each machine's tile class,
     * but a good example is in {@link TilePoweredFurnace#additionalItemModifier}
     * @param tier of booster
     * @return chance AND max amount of additional items
     */
    int getAdditionalItemModifier(int tier);

    /**
     * Should return an array of integers, typically starting from 1, of the amount
     * of tiers the booster has.
     * @return array with tiers of booster
     */
    int[] getTiers();

    /**
     * In order to know based on the booster given (without making assumptions!!) if
     * it can apply the modifiers of the booster, it needs to know *from the booster*
     * if it can.
     * @param tile In order for the booster item to really check if it can, an
     *             instance of the tile entity is given.
     * @param booster to check with the booster item
     * @return boolean value that defines if the booster can apply its modifiers to
     * the machine
     */
    boolean canBoosterApply(TileMachineBase tile, ItemStack booster);

}
