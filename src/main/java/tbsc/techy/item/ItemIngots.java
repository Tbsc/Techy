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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Generic class for adding ingots to the game.
 * Any ingots that are added need to register an instance of this class DIRECTLY.
 *
 * Created by tbsc on 5/26/16.
 */
public class ItemIngots extends ItemBase {

    public static final String IDENTIFIER = "itemIngot";

    public ItemIngots() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(64);
        setRegistryName(IDENTIFIER);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (IngotType ingotTy : IngotType.values()) {
            subItems.add(new ItemStack(itemIn, 1, ingotTy.id));
        }
    }

    @Override
    public void initModel(Item item) {
        ResourceLocation[] textures = new ResourceLocation[] {};
        for (IngotType type : IngotType.values()) {
            if (type != IngotType.COPPER)
                ArrayUtils.add(textures, new ResourceLocation(getRegistryName() + type.regName));
        }
        ModelBakery.registerItemVariants(this, textures);
        for (IngotType type : IngotType.values()) {
            ModelLoader.setCustomModelResourceLocation(this, type.id, new ModelResourceLocation(getRegistryName() + type.regName, "inventory"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.Techy:" + IngotType.valueOf(stack.getMetadata()).toString();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (stack.getMetadata() == IngotType.ALUMINIUM.id) {
            tooltip.add("Yes, ALUMINIUM. I don't care what you want, I call it aluminium.");
        }
    }

    public enum IngotType {
        COPPER("itemIngotCopper", "Copper", 0),
        TIN("itemIngotTin", "Tin", 1),
        SILVER("itemIngotSilver", "Silver", 2),
        ALUMINIUM("itemIngotAluminium", "Aluminium", 3),
        LITHIUM("itemIngotLithium", "Lithium", 4),
        BRONZE("itemIngotBronze", "Bronze", 5);

        public int id;
        public String name;
        public String regName;

        IngotType(String unlocalizedName, String registryName, int id) {
            this.id = id;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        public static IngotType valueOf(int id) {
            for (IngotType type : values()) {
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
