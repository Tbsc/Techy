package tbsc.techy.recipe;

import net.minecraft.item.ItemStack;

/**
 * Contains recipe data such as output, energy consumption and process time.
 *
 * Created by tbsc on 4/25/16.
 */
public class RecipeData {

    public ItemStack output;
    public int energyUsage;
    public int processTime;

    private RecipeData(ItemStack output, int energyUsage, int processTime) {
        this.output = output;
        this.energyUsage = energyUsage;
        this.processTime = processTime;
    }

    public static RecipeData createRecipeData(ItemStack output, int energyUsage, int processTime) {
        return new RecipeData(output, energyUsage, processTime);
    }

}
