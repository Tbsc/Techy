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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;
import tbsc.techy.api.register.IHasItemBlock;
import tbsc.techy.api.register.ITechyRegister;

import javax.annotation.Nonnull;

/**
 * Base block class, the superclass of all blocks from the Techy mod.
 *
 * Created by tbsc on 3/27/16.
 */
public class BlockBase extends Block implements ITechyRegister, IHasItemBlock {

    /**
     * It is protected since you shouldn't make an instance of BlockBase!
     * @param material Material of the block
     * @param registryName name of the block in game registry
     */
    protected BlockBase(Material material, String registryName) {
        super(material);
        setCreativeTab(Techy.tabTechyBlocks);
        setRegistryName(registryName);
        setUnlocalizedName(Techy.MODID + ":" + registryName);
    }

    /**
     * All blocks break the same, so this method makes sure all blocks from Techy will do so.
     * @param worldIn The world this is happening in
     * @param pos Position of the block
     * @param state The block(state)
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.destroyBlock(pos, true);
    }

    /**
     * Loads the model for the block dynamically.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void initModel(@Nonnull Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
