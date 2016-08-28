package tbsc.techy.api.registry.recipe.impl.out;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;
import tbsc.techy.api.registry.recipe.IRecipeOutput;

/**
 * API implementation of the {@link IRecipeOutput} interface, that adds support for multiple stack output.
 *
 * Created by tbsc on 8/3/16.
 */
public class DualStackRecipeOutput implements IRecipeOutput<Triple<ItemStack, ItemStack, Integer>> {

    private Triple<ItemStack, ItemStack, Integer> output;

    public DualStackRecipeOutput(Triple<ItemStack, ItemStack, Integer> output) {
        this.output = output;
    }

    public static DualStackRecipeOutput of(ItemStack firstOutput, ItemStack secondOutput, int secondOutputChance) {
        return new DualStackRecipeOutput(Triple.of(firstOutput, secondOutput, secondOutputChance));
    }

    @Override
    public Triple<ItemStack, ItemStack, Integer> getObject() {
        return output;
    }

}
