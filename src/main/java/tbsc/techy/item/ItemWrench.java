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
 * Techy's wrench.
 *
 * Created by tbsc on 3/27/16.
 */
public class ItemWrench extends ItemBase implements ITechyWrench {

    public ItemWrench() {
        super("itemWrench");
    }

    /**
     * If a "thing" (I say thing because it doesn't have to be a player) right clicks with the wrench, it checks
     * if the block that was hit is a wrenchable/rotatable block, and if it is then it attempts to do it.
     * @param stack The wrench {@link ItemStack}
     * @param playerIn The "player" who did the action
     * @param worldIn The world it happened in
     * @param pos Position of the block
     * @param side the side that got hit
     * @param hitX position on the block that got hit
     * @param hitY position on the block that got hit
     * @param hitZ position on the block that got hit
     * @return if should prevent from opening GUI
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState hitBlock = worldIn.getBlockState(pos); // Get the block that was hit
        if (playerIn.isSneaking()) { // If player is sneaking, dismantle instead of rotate
            if (hitBlock.getBlock() instanceof ITechyWrenchable) { // Check if wrenchable
                ITechyWrenchable wrenchBlock = (ITechyWrenchable) hitBlock.getBlock(); // Get the interface
                if (wrenchBlock.canDismantle(hitBlock, worldIn, pos, playerIn)) { // Make sure it can be dismantled
                    wrenchBlock.dismantleBlock(hitBlock, worldIn, pos, playerIn); // Dismantle
                    return true; // Prevent from opening the GUI
                }
            }
        } else { // Rotate, not sneaking
            if (hitBlock.getBlock() instanceof ITechyRotatable) { // If block is rotatable
                ITechyRotatable wrenchBlock = (ITechyRotatable) hitBlock.getBlock(); // Get the interface
                wrenchBlock.rotateBlock(hitBlock, worldIn, pos, side, playerIn); // Rotate
                return true; // Prevent from opening the GUI
            }
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }

}
