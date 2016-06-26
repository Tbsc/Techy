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
import tbsc.techy.block.BlockOreBase;
import tbsc.techy.item.ItemDusts;
import tbsc.techy.item.ItemIngots;

/**
 * Loads and contains the blocks (+ TileEntities) of Techy,
 *
 * Created by tbsc on 3/26/16.
 */
public class LegacyInit {

    // Instances of blocks
    public static BlockOreBase blockOreCopper;
    public static BlockOreBase blockOreTin;
    public static BlockOreBase blockOreSilver;
    public static BlockOreBase blockOreAluminium;
    public static BlockOreBase blockOreLithium;

    // Instances of items
    public static ItemIngots itemIngots;
    public static ItemDusts itemDusts;

    /**
     * Gets called on preInit stage and loads all of the ores
     */
    public static void init() {
        itemIngots = new ItemIngots();
        itemDusts = new ItemDusts();
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

        GameRegistry.register(itemIngots);
        GameRegistry.register(itemDusts);
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
    }

    /**
     * Loads models of blocks.
     * Each block needs to be added manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initLegacyModels() {
        blockOreCopper.initModel(Item.getItemFromBlock(blockOreCopper));
        blockOreTin.initModel(Item.getItemFromBlock(blockOreTin));
        blockOreSilver.initModel(Item.getItemFromBlock(blockOreSilver));
        blockOreAluminium.initModel(Item.getItemFromBlock(blockOreAluminium));
        blockOreLithium.initModel(Item.getItemFromBlock(blockOreLithium));

        itemDusts.initModel(itemDusts);
        itemIngots.initModel(itemIngots);
    }

}
