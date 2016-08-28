package tbsc.techy.api.registry.recipe.impl.in;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import tbsc.techy.api.registry.recipe.IRecipeInput;
import tbsc.techy.api.registry.recipe.IRecipeOutput;

/**
 * API implementation of the {@link IRecipeOutput} interface, that adds support for multiple stack input.
 *
 * Created by tbsc on 8/3/16.
 */
public class DualStackRecipeInput implements IRecipeInput<Pair<ItemStack, ItemStack>> {

    private Pair<ItemStack, ItemStack> input;

    public DualStackRecipeInput(Pair<ItemStack, ItemStack> input) {
        this.input = input;
    }

    public static DualStackRecipeInput of(ItemStack firstOutput, ItemStack secondOutput) {
        return new DualStackRecipeInput(Pair.of(firstOutput, secondOutput));
    }

    @Override
    public Pair<ItemStack, ItemStack> getObject() {
        return input;
    }

}
