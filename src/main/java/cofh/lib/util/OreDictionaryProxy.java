package cofh.lib.util;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Replacement class for Team CoFH's OreDictionaryProxy class.
 * Done so {@link cofh.lib.util.helpers.ItemHelper} would work on 1.8.9.
 * As Team CoFH (more specifically KingLemming) has specified, you should NOT initiate this
 * class or use it directly. Use {@link cofh.lib.util.helpers.ItemHelper} instead.
 *
 * Created by tbsc on 4/23/16.
 */
public class OreDictionaryProxy {

	public ItemStack getOre(String oreName) {

		if (!oreNameExists(oreName)) {
			return null;
		}
		return ItemHelper.cloneStack(OreDictionary.getOres(oreName).get(0), 1);
	}

    /**
     * Returns a list containing all of the ore names this {@link ItemStack} has.
     * @param stack to check
     * @return ore names this stack has (Note: list can be empty!)
     */
    public List<String> getOreNames(ItemStack stack) {
        List<String> oreNames = new ArrayList<>();
        int[] oreIDs = OreDictionary.getOreIDs(stack);
        for (int oreID : oreIDs) {
            oreNames.add(OreDictionary.getOreName(oreID));
        }
        return oreNames;
    }

    /**
     * Loops through the array of ore IDs of the {@link ItemStack}, and returns
     * whether any match was found.
     * @param stack To check
     * @param id to check
     * @return match was found
     */
    public boolean isOreIDEqual(ItemStack stack, int id) {
        boolean matchFound = false;
        int[] oreIDs = OreDictionary.getOreIDs(stack);
        for (int oreID : oreIDs) {
            if (oreID == id) {
                matchFound = true;
            }
        }
        return matchFound;
    }

    /**
     * Checks if this {@link ItemStack} has {@code oreName} registered.
     * @param stack to check
     * @param oreName to check
     * @return if it has it
     */
    public boolean isOreNameEqual(ItemStack stack, String oreName) {
        List<String> oreNames = getOreNames(stack);
        boolean matchFound = false;
        for (String stackOreName : oreNames) {
            if (stackOreName.equals(oreName)) {
                matchFound = true;
            }
        }
        return matchFound;
    }

    /**
     * Returns an integer array of the {@link ItemStack}'s ore IDs.
     * @param stack to check
     * @return integer array of ore IDs from {@code OreDictionary.getOreIDs(ItemStack)}
     */
    public int[] getOreIDs(ItemStack stack) {
        return OreDictionary.getOreIDs(stack);
    }

    /**
     * Loops through the list of ore names this {@link ItemStack} has, and checks
     * if any has {@code oreName} in the start.
     * @param stack to check
     * @param startPrefix what to check if it has in the start
     * @return if stack has ore name containing {@code oreName} in the start
     */
    public boolean containsOreNameStartWith(ItemStack stack, String startPrefix) {
        boolean matchFound = false;
        for (String fullOreName : getOreNames(stack)) {
            if (fullOreName.startsWith(startPrefix)) {
                matchFound = true;
            }
        }
        return matchFound;
    }

    /**
     * Using {@link ArrayUtils}, checks the {@link OreDictionary} ore name array and
     * sees if an entry corresponding to the specififed ore name exists.
     * @param oreName What ore name to check
     * @return If any item with that ore name exists in the {@link OreDictionary}
     */
    public boolean oreNameExists(String oreName) {
        return ArrayUtils.contains(OreDictionary.getOreNames(), oreName);
    }

}
