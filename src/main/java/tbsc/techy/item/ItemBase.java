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

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;
import tbsc.techy.api.register.ITechyRegister;

/**
 * Basic item class.
 * Contains basic model rendering, creative tab and names.
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class ItemBase extends Item implements ITechyRegister {

    protected ItemBase(String unlocalizedName) {
        setCreativeTab(Techy.tabTechyItems);
        setRegistryName(unlocalizedName);
        setUnlocalizedName(Techy.MODID + ":" + unlocalizedName);
    }

    /**
     * For subitems who have no unlocalized name, and they set it by their own
     */
    protected ItemBase() {
        setCreativeTab(Techy.tabTechyItems);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
