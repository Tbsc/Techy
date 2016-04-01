package tbsc.techy.api;

import net.minecraft.item.ItemStack;

/**
 * This interface will be used by recipes to declare the recipe.
 *
 * Created by tbsc on 3/31/16.
 */
public interface IMachineRecipe {

    /**
     * Each recipe has an identifier, which is used to find the recipe in the registry.
     * @return The identifier of the recipe
     */
    String getRecipeIdentifier();

    /**
     * The recipe's input {@link ItemStack}.
     * @return The input {@link ItemStack} of the recipe
     */
    ItemStack getInputStack();

    /**
     * The recipe's output {@link ItemStack}.
     * @return The output {@link ItemStack} of the recipe
     */
    ItemStack getOutputStack();

    /**
     * The amount of experience (in points, not levels) that should be given
     * for this recipe
     * @return amount of experience
     */
    int getExperienceAmount();

    /**
     * How much energy is consumed by doing this recipe
     * @return energy consumption amount
     */
    int getEnergyConsumption();

    /**
     * Whether the recipe should ignore the metadata
     * @return state
     */
    boolean shouldIgnoreInputMetadata();

    /**
     * Whether the recipe should ignore NBT
     * @return state
     */
    boolean shouldIgnoreInputNBT();

    /**
     * Whether it should ignore damage of the item
     * @return state
     */
    boolean shouldIgnoreInputDamage();

}
