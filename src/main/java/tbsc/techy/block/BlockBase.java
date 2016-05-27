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

/**
 * Base block class, the superclass of all blocks from the Techy mod.
 *
 * Created by tbsc on 3/27/16.
 */
public class BlockBase extends Block {

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
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
