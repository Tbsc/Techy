package tbsc.techy.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.api.iface.IHasGUI;
import tbsc.techy.api.wrench.ITechyWrench;
import tbsc.techy.api.wrench.Result;
import tbsc.techy.common.Techy;

/**
 * Base class for any Techy items. Contains a few base utilities for easing creation
 * of any future items. (Names, creative tab, etc.)
 *
 * Created by tbsc on 09/07/2016.
 */
public class ItemBase extends Item {

    /**
     * Unlike previous versions of Techy, constructors are now public because that way creation
     * of items can be done without having to create a class for adding a simple item.
     * @param regName The registry name that will be assigned to the item.
     */
    public ItemBase(String regName) {
        setRegistryName(regName);
        setUnlocalizedName(Techy.MODID + ":" + regName);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing hitSide, float hitX, float hitY, float hitZ) {
        // If any subclasses are wrenches, try to dismantle/rotate
        if (this instanceof ITechyWrench) {
            // Get wrench instance
            ITechyWrench wrench = (ITechyWrench) this;
            // Check if sneaking
            if (playerIn.isSneaking()) {
                // Check if can dismantle
                if (wrench.canDismantle(stack, playerIn, pos)) {
                    // Dismantle
                    if (wrench.dismantle(stack, playerIn, pos) == Result.SUCCESS) {
                        // Return success if succeeded
                        return EnumActionResult.SUCCESS;
                    } else {
                        // Return fail is failed
                        return EnumActionResult.FAIL;
                    }
                }
            } else {
                // Check if can rotate
                if (wrench.canRotate(stack, playerIn, pos, hitSide)) {
                    // Rotate
                    if (wrench.rotate(stack, playerIn, pos, hitSide) == Result.SUCCESS) {
                        // Return success if succeeded
                        return EnumActionResult.SUCCESS;
                    } else {
                        // Return fail is failed
                        return EnumActionResult.FAIL;
                    }
                }
            }
        } else if (this instanceof IHasGUI) {
            playerIn.openGui(Techy.instance, ((IHasGUI) this).getGUIID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, hitSide, hitX, hitY, hitZ);
    }

}
