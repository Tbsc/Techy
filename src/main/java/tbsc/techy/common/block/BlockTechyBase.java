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

package tbsc.techy.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import tbsc.techy.api.loader.IHasCustomModel;
import tbsc.techy.api.loader.IHasItemBlock;
import tbsc.techy.common.Techy;

/**
 * Base class for blocks. To allow for advanced functionality, use capabilities.
 *
 * Created by tbsc on 10/07/2016.
 */
public abstract class BlockTechyBase extends Block implements IHasItemBlock, IHasCustomModel {

    public BlockTechyBase(String regName, Material materialIn) {
        super(materialIn);
        setRegistryName(regName);
        setUnlocalizedName(Techy.MODID + ":" + regName);
        setCreativeTab(Techy.TAB_TECHY);
    }

    // Rendering

    @Override
    public void loadCustomModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    // Item block

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

}
