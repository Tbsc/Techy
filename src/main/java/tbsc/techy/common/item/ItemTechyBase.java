package tbsc.techy.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.capability.wrench.ITechyWrench;
import tbsc.techy.api.loader.IHasCustomModel;
import tbsc.techy.api.wrench.Result;
import tbsc.techy.common.Techy;

/**
 * Base class for any Techy items. Contains a few base utilities for easing creation
 * of any future items. (Names, creative tab, etc.)
 *
 * Created by tbsc on 09/07/2016.
 */
public class ItemTechyBase extends Item implements IHasCustomModel {

    /**
     * Unlike previous versions of Techy, constructors are now public because that way creation
     * of items can be done without having to create a class for adding a simple item.
     * @param regName The registry name that will be assigned to the item.
     */
    public ItemTechyBase(String regName) {
        setRegistryName(regName);
        setUnlocalizedName(Techy.MODID + ":" + regName);
        setCreativeTab(Techy.TAB_TECHY);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing hitSide, float hitX, float hitY, float hitZ) {
        // If item has wrench capability
        if (stack.hasCapability(TechyCapabilities.CAPABILITY_WRENCH, null)) {
            // Get wrench capability
            ITechyWrench wrench = stack.getCapability(TechyCapabilities.CAPABILITY_WRENCH, null);
            // Check if sneaking
            if (playerIn.isSneaking()) {
                // Break only on server, and the server will update client of change
                if (!worldIn.isRemote) {
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
        } else if (stack.hasCapability(TechyCapabilities.CAPABILITY_GUI, null)) { // Has GUI
            playerIn.openGui(Techy.instance, stack.getCapability(TechyCapabilities.CAPABILITY_GUI, null).getGUIID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, hand, hitSide, hitX, hitY, hitZ);
    }

    @Override
    public void loadCustomModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
