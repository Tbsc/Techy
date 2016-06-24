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

package tbsc.techy.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import tbsc.techy.init.BlockInit;
import tbsc.techy.item.ItemIngots;

/**
 * Adds all ores to the game
 *
 * Created by tbsc on 5/26/16.
 */
public class BlockOreBase extends BlockBase {

    public BlockOreBase(String unlocalizedName) {
        super(Material.ROCK, unlocalizedName);
        setHardness(3.0F);
        setResistance(5.0F);
    }

    public enum OreType {
        COPPER("blockOreCopper", "Copper", BlockInit.blockOreCopper, new ItemStack(ItemIngots.instance, 1, ItemIngots.IngotType.COPPER.id)),
        TIN("blockOreTin", "Tin", BlockInit.blockOreTin, new ItemStack(ItemIngots.instance, 1, ItemIngots.IngotType.TIN.id)),
        SILVER("blockOreSilver", "Silver", BlockInit.blockOreSilver, new ItemStack(ItemIngots.instance, 1, ItemIngots.IngotType.SILVER.id)),
        ALUMINIUM("blockOreAluminium", "Aluminium", BlockInit.blockOreAluminium, new ItemStack(ItemIngots.instance, 1, ItemIngots.IngotType.ALUMINIUM.id)),
        LITHIUM("blockOreLithium", "Lithium", BlockInit.blockOreLithium, new ItemStack(ItemIngots.instance, 1, ItemIngots.IngotType.LITHIUM.id));

        public BlockOreBase ore;
        public ItemStack ingot;
        public String name;
        public String regName;

        OreType(String unlocalizedName, String registryName, BlockOreBase ore, ItemStack ingot) {
            this.ore = ore;
            this.ingot = ingot;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
