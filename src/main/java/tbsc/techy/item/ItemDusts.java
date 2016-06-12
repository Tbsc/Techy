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

package tbsc.techy.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.init.ItemInit;

import java.util.List;

/**
 * Generic class for all dusts.
 *
 * Created by tbsc on 5/5/16.
 */
public class ItemDusts extends ItemBase {

    public ItemDusts() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(64);
        setRegistryName("itemDust");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (DustType dustType : DustType.values()) {
            subItems.add(new ItemStack(itemIn, 1, dustType.id));
        }
    }

    @Override
    public void initModel() {
        ResourceLocation[] textures = new ResourceLocation[] {};
        for (DustType type : DustType.values()) {
            if (type != DustType.IRON)
            ArrayUtils.add(textures, new ResourceLocation(getRegistryName() + type.regName));
        }
        ModelBakery.registerItemVariants(this, textures);
        for (DustType type : DustType.values()) {
            ModelLoader.setCustomModelResourceLocation(this, type.id, new ModelResourceLocation(getRegistryName() + type.regName, "inventory"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.Techy:" + DustType.valueOf(stack.getMetadata()).toString();
    }

    public enum DustType {

        IRON("itemDustIron", "Iron", 0, new ItemStack(Items.IRON_INGOT)),
        GOLD("itemDustGold", "Gold", 1, new ItemStack(Items.GOLD_INGOT)),
        DIAMOND("itemDustDiamond", "Diamond", 2, new ItemStack(Items.DIAMOND)),
        COAL("itemDustCoal", "Coal", 3, new ItemStack(Items.COAL)),
        EMERALD("itemDustEmerald", "Emerald", 4, new ItemStack(Items.EMERALD)),
        WOOD("itemDustWood", "Wood", 5, null),
        STONE("itemDustStone", "Stone", 6, null),
        COPPER("itemDustCopper", "Copper", 7, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.COPPER.id)),
        TIN("itemDustTin", "Tin", 8, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.TIN.id)),
        SILVER("itemDustSilver", "Silver", 9, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.SILVER.id)),
        ALUMINIUM("itemDustAluminium", "Aluminium", 10, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.ALUMINIUM.id)),
        LITHIUM("itemDustLithium", "Lithium", 11, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.LITHIUM.id)),
        BRONZE("itemDustBronze", "Bronze", 12, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.BRONZE.id));

        public int id;
        public String name;
        public String regName;
        public ItemStack ingot;

        DustType(String unlocalizedName, String registryName, int id, ItemStack ingot) {
            this.id = id;
            this.name = unlocalizedName;
            this.regName = registryName;
            this.ingot = ingot;
        }

        public static DustType valueOf(int id) {
            for (DustType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
