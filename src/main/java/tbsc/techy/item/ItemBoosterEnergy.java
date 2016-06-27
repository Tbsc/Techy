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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tbsc.techy.tile.TileMachineBase;

import java.util.List;

/**
 * Booster for reducing energy usage, in return experience is reduced and time is increased.
 *
 * Created by tbsc on 5/2/16.
 */
public class ItemBoosterEnergy extends ItemBoosterBase {

    public static final String IDENTIFIER = "itemBoosterEnergy";

    public ItemBoosterEnergy() {
        super(IDENTIFIER);

        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(itemIn, 1, 1));
        subItems.add(new ItemStack(itemIn, 1, 2));
        subItems.add(new ItemStack(itemIn, 1, 3));
    }

    @Override
    public void initModel() {
        ModelBakery.registerItemVariants(this, new ResourceLocation(getRegistryName() + "T0"), new ResourceLocation(getRegistryName() + "T1"), new ResourceLocation(getRegistryName() + "T2"), new ResourceLocation(getRegistryName() + "T3"));
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "T0", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "T1", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(getRegistryName() + "T2", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 3, new ModelResourceLocation(getRegistryName() + "T3", "inventory"));
    }

    @Override
    public int getEnergyModifier(int tier) {
        switch (tier) {
            case 0:
                return 125;
            case 1:
                return -15;
            case 2:
                return -40;
            case 3:
                return -75;
            default:
                return 0;
        }
    }

    @Override
    public int getTimeModifier(int tier) {
        switch (tier) {
            case 0:
                return 0;
            case 1:
                return 15;
            case 2:
                return 30;
            case 3:
                return 55;
            default:
                return 0;
        }
    }

    @Override
    public int getExperienceModifier(int tier) {
        switch (tier) {
            case 0:
                return 0;
            case 1:
                return -10;
            case 2:
                return -25;
            case 3:
                return -65;
            default:
                return 0;
        }
    }

    @Override
    public int getAdditionalItemModifier(int tier) {
        return 0;
    }

    @Override
    public int[] getTiers() {
        return new int[] {
                0, 1, 2, 3
        };
    }

    @Override
    public boolean canBoosterApply(TileMachineBase tile, ItemStack booster) {
        return true;
    }

}
