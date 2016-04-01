package tbsc.techy.machine.furnace;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import tbsc.techy.api.IMachineRecipe;
import tbsc.techy.recipe.RecipeRegistryBase;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Recipe registry implementation for the powered furnace.
 *
 * Created by tbsc on 3/31/16.
 */
public class RecipeRegistryFurnace extends RecipeRegistryBase {

    protected ConcurrentHashMap<String, IMachineRecipe> recipeMap;

    private RecipeRegistryFurnace() {
        instance = this;
        recipeMap = new ConcurrentHashMap<>();
        FMLLog.info("Powered Furnace recipe handler initiated!");
    }

    @Override
    public void addRecipe(IMachineRecipe recipe) {
        if (recipe != null) {
            recipeMap.put(recipe.getRecipeIdentifier(), recipe);
            FMLLog.log(Level.DEBUG, "Recipe %s added to the powered furnace recipe handler", recipe.getRecipeIdentifier());
        }
    }

    @Override
    public void removeRecipe(String identifier) {
        if (recipeMap.containsKey(identifier)) {
            recipeMap.remove(identifier);
            FMLLog.warning("Recipe %s removed from the powered furnace recipe handler!", identifier);
        }
    }

    @Override
    public void removeRecipe(ItemStack input) {
        for (IMachineRecipe recipe : recipeMap.values()) {
            if (checkInputsAreMatching(input, recipe)) {
                recipeMap.remove(recipe.getRecipeIdentifier());
            }
        }
    }

    @Override
    public void removeRecipe(IMachineRecipe recipe) {
        if (recipeMap.contains(recipe)) {
            recipeMap.remove(recipe.getRecipeIdentifier());
            FMLLog.warning("Recipe %s removed from the powered furnace recipe handler!", recipe.getRecipeIdentifier());
        }
    }

}
