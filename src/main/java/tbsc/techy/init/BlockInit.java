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

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.block.BlockMachineBase;
import tbsc.techy.block.BlockOreBase;
import tbsc.techy.block.pipe.BlockPipeEnergy;
import tbsc.techy.machine.crusher.BlockCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.machine.generator.coal.BlockCoalGenerator;
import tbsc.techy.machine.generator.coal.TileCoalGenerator;
import tbsc.techy.tile.pipe.TilePipeEnergy;

/**
 * Loads and contains the blocks (+ TileEntities) of Techy,
 *
 * Created by tbsc on 3/26/16.
 */
public class BlockInit {

    // Instances of blocks
    public static BlockBaseFacingMachine blockPoweredFurnace;
    public static BlockBaseFacingMachine blockCrusher;
    public static BlockMachineBase blockMachineBaseBasic;
    public static BlockMachineBase blockMachineBaseImproved;
    public static BlockMachineBase blockMachineBaseAdvanced;
    public static BlockPipeEnergy blockPipeEnergyBasic;
    public static BlockPipeEnergy blockPipeEnergyImproved;
    public static BlockPipeEnergy blockPipeEnergyAdvanced;
    public static BlockBaseFacingMachine blockCoalGenerator;
    public static BlockOreBase blockOreCopper;
    public static BlockOreBase blockOreTin;
    public static BlockOreBase blockOreSilver;
    public static BlockOreBase blockOreAluminium;
    public static BlockOreBase blockOreLithium;

    /**
     * Gets called on preInit stage and loads all of the blocks and TileEntities.
     */
    public static void init() {
        blockPoweredFurnace = new BlockPoweredFurnace();
        blockCrusher = new BlockCrusher();
        blockMachineBaseBasic = new BlockMachineBase("blockMachineBaseBasic");
        blockMachineBaseImproved = new BlockMachineBase("blockMachineBaseImproved");
        blockMachineBaseAdvanced = new BlockMachineBase("blockMachineBaseAdvanced");
        blockPipeEnergyBasic = new BlockPipeEnergy("blockPipeEnergyBasic", 160 * 6, 360); // Can transfer 2 coal generators, and can contain enough for 2 from all sides
        blockPipeEnergyImproved = new BlockPipeEnergy("blockPipeEnergyImproved", 360 * 4, 1280); // max 4 sides
        blockPipeEnergyAdvanced = new BlockPipeEnergy("blockPipeEnergyAdvanced", 1280 * 2, 3840); // max 2 sides
        blockCoalGenerator = new BlockCoalGenerator();
        blockOreCopper = new BlockOreBase("blockOreCopper");
        blockOreCopper.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        blockOreTin = new BlockOreBase("blockOreTin");
        blockOreTin.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        blockOreSilver = new BlockOreBase("blockOreSilver");
        blockOreSilver.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        blockOreAluminium = new BlockOreBase("blockOreAluminium");
        blockOreAluminium.setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        blockOreLithium = new BlockOreBase("blockOreLithium");
        blockOreLithium.setHarvestLevel("pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel());

        GameRegistry.register(blockPoweredFurnace);
        GameRegistry.register(new ItemBlock(blockPoweredFurnace), blockPoweredFurnace.getRegistryName());
        GameRegistry.register(blockCrusher);
        GameRegistry.register(new ItemBlock(blockCrusher), blockCrusher.getRegistryName());
        GameRegistry.register(blockMachineBaseBasic);
        GameRegistry.register(new ItemBlock(blockMachineBaseBasic), blockMachineBaseBasic.getRegistryName());
        GameRegistry.register(blockMachineBaseImproved);
        GameRegistry.register(new ItemBlock(blockMachineBaseImproved), blockMachineBaseImproved.getRegistryName());
        GameRegistry.register(blockMachineBaseAdvanced);
        GameRegistry.register(new ItemBlock(blockMachineBaseAdvanced), blockMachineBaseAdvanced.getRegistryName());
        GameRegistry.register(blockPipeEnergyBasic);
        GameRegistry.register(new ItemBlock(blockPipeEnergyBasic), blockPipeEnergyBasic.getRegistryName());
        GameRegistry.register(blockPipeEnergyImproved);
        GameRegistry.register(new ItemBlock(blockPipeEnergyImproved), blockPipeEnergyImproved.getRegistryName());
        GameRegistry.register(blockPipeEnergyAdvanced);
        GameRegistry.register(new ItemBlock(blockPipeEnergyAdvanced), blockPipeEnergyAdvanced.getRegistryName());
        GameRegistry.register(blockCoalGenerator);
        GameRegistry.register(new ItemBlock(blockCoalGenerator), blockCoalGenerator.getRegistryName());
        GameRegistry.register(blockOreCopper);
        GameRegistry.register(new ItemBlock(blockOreCopper), blockOreCopper.getRegistryName());
        GameRegistry.register(blockOreTin);
        GameRegistry.register(new ItemBlock(blockOreTin), blockOreTin.getRegistryName());
        GameRegistry.register(blockOreSilver);
        GameRegistry.register(new ItemBlock(blockOreSilver), blockOreSilver.getRegistryName());
        GameRegistry.register(blockOreAluminium);
        GameRegistry.register(new ItemBlock(blockOreAluminium), blockOreAluminium.getRegistryName());
        GameRegistry.register(blockOreLithium);
        GameRegistry.register(new ItemBlock(blockOreLithium), blockOreLithium.getRegistryName());

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
        GameRegistry.registerTileEntity(TileCrusher.class, "tileCrusher");
        GameRegistry.registerTileEntity(TilePipeEnergy.class, "pipeEnergy");
        GameRegistry.registerTileEntity(TileCoalGenerator.class, "tileCoalGenerator");
    }

    /**
     * Loads models of blocks.
     * Each block needs to be added manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPoweredFurnace.initModel();
        blockCrusher.initModel();
        blockMachineBaseBasic.initModel();
        blockMachineBaseImproved.initModel();
        blockMachineBaseAdvanced.initModel();
        blockPipeEnergyBasic.initModel();
        blockPipeEnergyImproved.initModel();
        blockPipeEnergyAdvanced.initModel();
        blockCoalGenerator.initModel();
        blockOreCopper.initModel();
        blockOreTin.initModel();
        blockOreSilver.initModel();
        blockOreAluminium.initModel();
        blockOreLithium.initModel();
    }

}
