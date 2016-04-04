package tbsc.techy.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tbsc.techy.api.ITechyRotatable;
import tbsc.techy.api.ITechyWrench;
import tbsc.techy.api.ITechyWrenchable;

/**
 * Created by tbsc on 3/27/16.
 */
public class ItemWrench extends ItemBase implements ITechyWrench {

    public ItemWrench() {
        super("itemWrench");
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState hitBlock = worldIn.getBlockState(pos); // Get the block that was hit
        if (playerIn.isSneaking()) { // If player is sneaking, dismantle instead of rotate
            if (hitBlock.getBlock() instanceof ITechyWrenchable) { // Check if wrenchable
                ITechyWrenchable wrenchBlock = (ITechyWrenchable) hitBlock.getBlock(); // Get the interface
                if (wrenchBlock.canDismantle(hitBlock, worldIn, pos, playerIn)) { // Make sure it can be dismantled
                    wrenchBlock.dismantleBlock(hitBlock, worldIn, pos, playerIn); // Dismantle
                }
            }
        } else { // Rotate, not sneaking
            if (hitBlock.getBlock() instanceof ITechyRotatable) { // If block is rotatable
                ITechyRotatable wrenchBlock = (ITechyRotatable) hitBlock.getBlock(); // Get the interface
                wrenchBlock.rotateBlock(hitBlock, worldIn, pos, side, playerIn); // Rotate
            }
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }

}
