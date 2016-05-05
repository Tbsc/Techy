package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created as a wrapper for recipe inputs, to allow support for ore dictionary inputs.
 *
 * Created by tbsc on 5/5/16.
 */
public class StackRecipeInput<T extends ItemStack> implements IRecipeInput {

    public T input;

    public StackRecipeInput(T input) {
        this.input = input;
    }

    public static StackRecipeInput<ItemStack> of(ItemStack data) {
        return new StackRecipeInput<>(data);
    }

    @Override
    public T getInput() {
        return input;
    }
}
