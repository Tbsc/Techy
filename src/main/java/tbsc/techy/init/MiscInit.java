package tbsc.techy.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tbsc.techy.Techy;
import tbsc.techy.client.gui.TechyGuiHandler;
import tbsc.techy.event.GeneralEventHandler;
import tbsc.techy.recipe.CrusherRecipes;
import tbsc.techy.recipe.PoweredFurnaceRecipes;

/**
 * Anything that isn't a block/tile/item and that it needs to get loaded on startup goes here.
 *
 * Created by tbsc on 3/26/16.
 */
public class MiscInit {

    /**
     * Loads everything needed to be run. (Such as events and GUI handlers)
     * Also adds recipes.
     * _______
     * RECIPES
     * ‾‾‾‾‾‾‾
     * All machine recipes will not require more then 3 ingot types *TOTAL*.
     * A machine recipe must contain at least 1 block item (as a base), and
     * other items can be whatever.
     * Recipes need to make sense. For example, a powered furnace will
     * need a regular furnace.
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(Techy.instance, new TechyGuiHandler());

        // MACHINES //

        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockPoweredFurnace,
                "SIS",
                "BFB",
                "SHS",
                'F', Blocks.furnace, 'H', ItemInit.itemHeatingComponent, 'B', ItemInit.itemBatterySmall, 'S', "stone", 'I', "ingotIron"));

        // CRAFTING COMPONENTS //

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemHeatingComponent,
                "III",
                "IFI",
                "RRR",
                'F', Items.flint_and_steel, 'I', "ingotGold", 'R', "dustRedstone"));

        // ITEMS //

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatterySmall,
                " I ",
                "IRI",
                "III",
                'I', "ingotIron", 'R', "dustRedstone")); // No dust because at that tier you don't have a crusher yet
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatteryMedium,
                " D ",
                "GRG",
                "GGG",
                'D', "dustGold", 'G', "ingotGold", 'R', ItemInit.itemBatterySmall));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatteryMedium,
                " D ",
                "GRG",
                "GGG",
                'D', "dustDiamond", 'G', "gemDiamond", 'R', ItemInit.itemBatteryMedium));
    }

    /**
     * Everything that needs to be run late.
     */
    public static void postInit() {
        PoweredFurnaceRecipes.instance().loadVanillaRecipes();
        PoweredFurnaceRecipes.instance().loadModRecipes();
        CrusherRecipes.instance().loadModRecipes();
    }

}
