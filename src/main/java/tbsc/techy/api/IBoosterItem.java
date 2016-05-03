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
     * Amount of energy consumed is divided by this integer.
     * @param tier of booster
     * @return energy modifier to divide by
     */
    double getEnergyModifier(int tier);

    /**
     * Modifier that defines total time for operations.
     * @param tier of booster
     * @return time modifier to divide by
     */
    double getTimeModifier(int tier);

    /**
     * How much experience should be given. Amount of experience given is multiplied
     * by the modifier.
     * @param tier of booster
     * @return experience multiplier
     */
    double getExperienceModifier(int tier);

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
