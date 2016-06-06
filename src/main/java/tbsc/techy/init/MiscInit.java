package tbsc.techy.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
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

import java.util.Random;

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
    public static void preInit() {
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

        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockCoalGenerator,
                "IAI",
                "BFB",
                "IGI",
                'I', "ingotIron", 'A', "ingotAluminum", 'F', BlockInit.blockMachineBaseBasic, 'B', ItemInit.itemBatterySmall, 'G', ItemInit.itemIgnitionComponent));

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
                'F', Items.IRON_PICKAXE, 'I', "ingotGold", 'S', "ingotSilver", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemIgnitionComponent,
                "III",
                "SFS",
                "RRR",
                'F', Items.COAL, 'I', "ingotGold", 'S', "ingotSilver", 'R', "dustRedstone"));

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
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemBatteryLarge,
                " D ",
                "GRG",
                "GLG",
                'D', "dustDiamond", 'G', "gemDiamond", 'L', "dustLithium", 'R', ItemInit.itemBatteryMedium));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.BRONZE.id),
                "dustCopper", "dustTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockPipeEnergyBasic,
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustCopper", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockPipeEnergyImproved,
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustSilver", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlockInit.blockPipeEnergyAdvanced,
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustLithium", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemWrench,
                "T  ",
                " I ",
                "  I",
                'T', "ingotTin", 'I', "ingotIron"));

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
    }

    public static OreWorldGenerator copperGenerator, tinGenerator, silverGenerator, aluminiumGenerator, lithiumGenerator;

    /**
     * Init stage
     */
    public static void init() {
        GameRegistry.registerWorldGenerator(copperGenerator = new OreWorldGenerator(BlockInit.blockOreCopper.getDefaultState(),
                ConfigData.copperPerVein, ConfigData.copperMaxHeight, ConfigData.copperPerChunk), 0);
        GameRegistry.registerWorldGenerator(tinGenerator = new OreWorldGenerator(BlockInit.blockOreTin.getDefaultState(),
                ConfigData.tinPerVein, ConfigData.tinMaxHeight, ConfigData.tinPerChunk), 0);
        GameRegistry.registerWorldGenerator(silverGenerator = new OreWorldGenerator(BlockInit.blockOreSilver.getDefaultState(),
                ConfigData.silverPerVein, ConfigData.silverMaxHeight, ConfigData.silverPerChunk), 0);
        GameRegistry.registerWorldGenerator(aluminiumGenerator = new OreWorldGenerator(BlockInit.blockOreAluminium.getDefaultState(),
                ConfigData.aluminiumPerVein, ConfigData.aluminiumMaxHeight, ConfigData.aluminiumPerChunk), 0);
        GameRegistry.registerWorldGenerator(lithiumGenerator = new OreWorldGenerator(BlockInit.blockOreLithium.getDefaultState(),
                ConfigData.lithiumPerVein, ConfigData.lithiumMaxHeight, ConfigData.lithiumPerChunk), 0);
    }

    /**
     * Generates all of Techy's ore world gen in the world.
     */
    public static void generateOres(World world, int radius, ChunkPos chunkPos) {
        Random rand = new Random(world.getSeed());
        for (int x = 0; x < radius; ++x) {
            int chunkX = x + chunkPos.chunkXPos;
            for (int z = 0; z < radius; ++z) {
                int chunkZ = z + chunkPos.chunkZPos;
                FMLLog.info("Retro-genning ores in chunk x=" + chunkX + ", z=" + chunkZ);
                copperGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                tinGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                silverGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                aluminiumGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                lithiumGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
            }
        }
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
