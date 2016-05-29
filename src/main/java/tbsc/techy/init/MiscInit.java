package tbsc.techy.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tbsc.techy.ConfigData;
import tbsc.techy.Techy;
import tbsc.techy.block.BlockOreBase;
import tbsc.techy.client.gui.TechyGuiHandler;
import tbsc.techy.event.GeneralEventHandler;
import tbsc.techy.item.ItemDusts;
import tbsc.techy.item.ItemIngots;
import tbsc.techy.misc.OreWorldGenerator;
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

        // Adding dusts to ore dictionary
        for (ItemDusts.DustType dustType : ItemDusts.DustType.values()) {
            OreDictionary.registerOre("dust" + dustType.regName, new ItemStack(ItemInit.itemDusts, 1, dustType.id));
        }
        // Adding ingots to ore dictionary
        for (ItemIngots.IngotType ingotType : ItemIngots.IngotType.values()) {
            OreDictionary.registerOre("ingot" + ingotType.regName, new ItemStack(ItemInit.itemIngots, 1, ingotType.id));
        }
        // Adding ores to ore dictionary
        for (BlockOreBase.OreType oreType : BlockOreBase.OreType.values()) {
            OreDictionary.registerOre("ore" + oreType.regName, new ItemStack(oreType.ore));
        }
        // Since I need to be compatible with the ore dictionary, I am also registering it as aluminium
        OreDictionary.registerOre("oreAluminium", new ItemStack(BlockOreBase.OreType.ALUMINUM.ore));
        OreDictionary.registerOre("ingotAluminium", new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.ALUMINIUM.id));
        OreDictionary.registerOre("dustAluminium", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.ALUMINIUM.id));

        // MACHINES //

        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockPoweredFurnace,
                "SIS",
                "BFB",
                "SHS",
                'F', BlockInit.blockMachineBaseBasic, 'H', ItemInit.itemHeatingComponent, 'B', ItemInit.itemBatterySmall, 'S', "ingotSilver", 'I', "ingotIron"));

        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockCrusher,
                "IAI",
                "BFB",
                "IGI",
                'F', BlockInit.blockMachineBaseBasic, 'B', ItemInit.itemBatterySmall, 'G', ItemInit.itemGrindingComponent, 'A', "ingotGold", 'I', "ingotTin"));

        // MACHINE BASES //

        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockMachineBaseBasic,
                "ACA",
                "CIC",
                "ACA",
                'C', "ingotCopper", 'A', "ingotAluminium", 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockMachineBaseImproved,
                "ACA",
                "CBC",
                "ACA",
                'C', "ingotBronze", 'A', "ingotAluminium", 'B', BlockInit.blockMachineBaseBasic));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockMachineBaseAdvanced,
                "ACA",
                "CBC",
                "ACA",
                'C', "ingotLithium", 'A', "ingotAluminium", 'B', BlockInit.blockMachineBaseImproved));

        // CRAFTING COMPONENTS //

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemHeatingComponent,
                "III",
                "SFS",
                "RRR",
                'F', Items.FLINT_AND_STEEL, 'I', "ingotGold", 'S', "ingotSilver", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemGrindingComponent,
                "III",
                "SFS",
                "RRR",
                'F', Items.STONE_PICKAXE, 'I', "ingotGold", 'S', "ingotSilver", 'R', "dustRedstone"));

        // ITEMS //

        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatterySmall,
                " I ",
                "IRI",
                "ICI",
                'I', "ingotIron", 'C', "dustCopper", 'R', "dustRedstone")); // No dust because at that tier you don't have a crusher yet
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatteryMedium,
                " D ",
                "GRG",
                "GSG",
                'D', "dustGold", 'S', "dustSilver", 'G', "ingotGold", 'R', ItemInit.itemBatterySmall));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatteryMedium,
                " D ",
                "GRG",
                "GLG",
                'D', "dustDiamond", 'G', "gemDiamond", 'L', "dustLithium", 'R', ItemInit.itemBatteryMedium));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.BRONZE.id),
                "dustCopper", "dustTin"));

        // FURNACE RECIPES //

        for (BlockOreBase.OreType ore : BlockOreBase.OreType.values()) {
            if (ore.ore != BlockInit.blockOreLithium) { // ONLY add if ore isn't lithium
                GameRegistry.addSmelting(ore.ore, ore.ingot, 2);
            }
        }
        for (ItemDusts.DustType dust : ItemDusts.DustType.values()) {
            if (dust.ingot != null) { // Not all dusts have an ingot form
                if (dust.ingot.getMetadata() != ItemIngots.IngotType.LITHIUM.id) { // ONLY add if dust isn't lithium
                    GameRegistry.addSmelting(new ItemStack(ItemInit.itemDusts, 1, dust.id), dust.ingot, 2);
                }
            }
        }

        GameRegistry.registerWorldGenerator(new OreWorldGenerator(BlockInit.blockOreCopper.getDefaultState(),
                ConfigData.copperPerVein, ConfigData.copperMaxHeight, ConfigData.copperPerChunk), 0);
        GameRegistry.registerWorldGenerator(new OreWorldGenerator(BlockInit.blockOreTin.getDefaultState(),
                ConfigData.tinPerVein, ConfigData.tinMaxHeight, ConfigData.tinPerChunk), 0);
        GameRegistry.registerWorldGenerator(new OreWorldGenerator(BlockInit.blockOreSilver.getDefaultState(),
                ConfigData.silverPerVein, ConfigData.silverMaxHeight, ConfigData.silverPerChunk), 0);
        GameRegistry.registerWorldGenerator(new OreWorldGenerator(BlockInit.blockOreAluminium.getDefaultState(),
                ConfigData.aluminiumPerVein, ConfigData.aluminiumMaxHeight, ConfigData.aluminiumPerChunk), 0);
        GameRegistry.registerWorldGenerator(new OreWorldGenerator(BlockInit.blockOreLithium.getDefaultState(),
                ConfigData.lithiumPerVein, ConfigData.lithiumMaxHeight, ConfigData.lithiumPerChunk), 0);
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
