/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        MinecraftForge.EVENT_BUS.register(Techy.instance);
        NetworkRegistry.INSTANCE.registerGuiHandler(Techy.instance, new TechyGuiHandler());
    }

    public static OreWorldGenerator copperGenerator, tinGenerator, silverGenerator, aluminiumGenerator, lithiumGenerator;

    /**
     * Init stage
     */
    public static void init() {
        if (ConfigData.shouldGenerateCopper) {
            GameRegistry.registerWorldGenerator(copperGenerator = new OreWorldGenerator(BlockInit.blockOreCopper.getDefaultState(),
                    ConfigData.copperPerVein, ConfigData.copperMaxHeight, ConfigData.copperPerChunk), 0);
        }
        if (ConfigData.shouldGenerateTin) {
            GameRegistry.registerWorldGenerator(tinGenerator = new OreWorldGenerator(BlockInit.blockOreTin.getDefaultState(),
                    ConfigData.tinPerVein, ConfigData.tinMaxHeight, ConfigData.tinPerChunk), 0);
        }
        if (ConfigData.shouldGenerateSilver) {
            GameRegistry.registerWorldGenerator(silverGenerator = new OreWorldGenerator(BlockInit.blockOreSilver.getDefaultState(),
                    ConfigData.silverPerVein, ConfigData.silverMaxHeight, ConfigData.silverPerChunk), 0);
        }
        if (ConfigData.shouldGenerateAluminium) {
            GameRegistry.registerWorldGenerator(aluminiumGenerator = new OreWorldGenerator(BlockInit.blockOreAluminium.getDefaultState(),
                    ConfigData.aluminiumPerVein, ConfigData.aluminiumMaxHeight, ConfigData.aluminiumPerChunk), 0);
        }
        if (ConfigData.shouldGenerateLithium) {
            GameRegistry.registerWorldGenerator(lithiumGenerator = new OreWorldGenerator(BlockInit.blockOreLithium.getDefaultState(),
                    ConfigData.lithiumPerVein, ConfigData.lithiumMaxHeight, ConfigData.lithiumPerChunk), 0);
        }

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
                'F', BlockInit.blockMachineBaseBasic, 'B', ItemInit.itemBatterySmall, 'G', ItemInit.itemIgnitionComponent, 'A', "ingotAluminium", 'I', "ingotIron"));

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
                'I', "ingotIron", 'C', "ingotCopper", 'R', "dustRedstone")); // No dust because at that tier you don't have a crusher yet
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
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockInit.blockPipeEnergyBasic, 8),
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustCopper", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockInit.blockPipeEnergyImproved, 8),
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustSilver", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockInit.blockPipeEnergyAdvanced, 8),
                "GGG",
                "CRC",
                "GGG",
                'G', Blocks.GLASS, 'C', "dustLithium", 'R', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(ItemInit.itemWrench,
                "T  ",
                " I ",
                "  I",
                'T', "ingotTin", 'I', "ingotIron"));

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
        OreDictionary.registerOre("oreAluminum", new ItemStack(BlockOreBase.OreType.ALUMINIUM.ore));
        OreDictionary.registerOre("ingotAluminum", new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.ALUMINIUM.id));
        OreDictionary.registerOre("dustAluminum", new ItemStack(ItemInit.itemDusts, 1, ItemDusts.DustType.ALUMINIUM.id));

        // FURNACE RECIPES //

        for (BlockOreBase.OreType ore : BlockOreBase.OreType.values()) {
            if (ore.ore != BlockInit.blockOreLithium) { // ONLY add if ore isn't lithium
                GameRegistry.addSmelting(ore.ore, ore.ingot, 2);
            }
        }
        for (ItemDusts.DustType dust : ItemDusts.DustType.values()) {
            if (dust.ingot != null) { // Not all dusts have an ingot form
                if (dust.id != ItemDusts.DustType.LITHIUM.id) { // ONLY add if dust isn't lithium
                    GameRegistry.addSmelting(new ItemStack(ItemInit.itemDusts, 1, dust.id), dust.ingot, 2);
                }
            }
        }
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
                if (ConfigData.shouldGenerateCopper) {
                    copperGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                }
                if (ConfigData.shouldGenerateTin) {
                    tinGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                }
                if (ConfigData.shouldGenerateSilver) {
                    silverGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                }
                if (ConfigData.shouldGenerateAluminium) {
                    aluminiumGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                }
                if (ConfigData.shouldGenerateLithium) {
                    lithiumGenerator.generate(rand, chunkX, chunkZ, world, world.provider.createChunkGenerator(), world.getChunkProvider());
                }
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
