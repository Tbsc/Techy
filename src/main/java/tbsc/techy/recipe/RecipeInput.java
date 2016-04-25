package tbsc.techy.recipe;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;

/**
 * Basic "recipe input" class. It can be either a string (ore name) or an ItemStack (item).
 *
 * Created by tbsc on 4/25/16.
 */
public class RecipeInput {

    public ItemStack input;

    private RecipeInput(ItemStack input) {
        this.input = input;
    }

    public boolean isInputEqual(RecipeInput other) {
        return other != null && input.getItem() == other.input.getItem() && input.getMetadata() == other.input.getMetadata();
    }

    public static RecipeInput createOreDictInput(String oreName, int stackSize, int metadata) {
        ItemStack oreStack = ItemHelper.getOre(oreName);
        if (oreStack == null) return null;
        oreStack.stackSize = stackSize;
        oreStack.setItemDamage(metadata);
        return new RecipeInput(oreStack);
    }

    public static RecipeInput createItemStackInput(ItemStack input) {
        if (input == null) return null;
        return new RecipeInput(new ItemStack(input.getItem(), input.stackSize, input.getMetadata()));
    }

}
