package tbsc.techy.api.capability.wrench;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.api.capability.TechyCapabilities;
import tbsc.techy.api.capability.dismantle.ITechyDismantleable;
import tbsc.techy.api.wrench.Result;

/**
 * Default implementation of the Techy wrench capability interface, {@link ITechyWrench}.
 *
 * Created by tbsc on 10/07/2016.
 */
public class TechyWrench implements ITechyWrench {

    /**
     * Return true if the block's valid rotation sides contain the side given.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @param rotateTo Side to rotate the target to
     * @return Can the block rotate to the side given
     */
    @Override
    public boolean canRotate(ItemStack wrench, EntityLivingBase entity, BlockPos target, EnumFacing rotateTo) {
        World world = entity.worldObj;
        return world.isBlockLoaded(target) && ArrayUtils.contains(entity.worldObj.getBlockState(target).getBlock().getValidRotations(entity.worldObj, target), rotateTo);
    }

    /**
     * Using the Forge provided methods, rotates the block at the target given.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @param rotateTo Side to rotate the target to
     * @return Has rotation succeeded
     */
    @Override
    public Result rotate(ItemStack wrench, EntityLivingBase entity, BlockPos target, EnumFacing rotateTo) {
        World world = entity.worldObj;
        return world.getBlockState(target).getBlock().rotateBlock(world, target, rotateTo) ? Result.SUCCESS : Result.FAIL;
    }

    /**
     * Checks if the target block can be dismantled.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return can target block be dismantled
     */
    @Override
    public boolean canDismantle(ItemStack wrench, EntityLivingBase entity, BlockPos target) {
        IBlockState state = entity.worldObj.getBlockState(target);
        // Make sure block is loaded, support capabilities, and has the dismantleable capability
        return (entity.worldObj.isBlockLoaded(target) && state.getBlock() instanceof ICapabilityProvider) && ((ICapabilityProvider) state.getBlock()).hasCapability(TechyCapabilities.CAPABILITY_DISMANTLEABLE, null);
    }

    /**
     * Dismantles the target block.
     * @param wrench The wrench {@link ItemStack}
     * @param entity The entity using the wrench
     * @param target The position the entity targets
     * @return Has the process succeeded
     */
    @Override
    public Result dismantle(ItemStack wrench, EntityLivingBase entity, BlockPos target) {
        World world = entity.worldObj;
        IBlockState state = entity.worldObj.getBlockState(target);
        ITechyDismantleable dismantle = ((ICapabilityProvider) state.getBlock()).getCapability(TechyCapabilities.CAPABILITY_DISMANTLEABLE, null);
        return dismantle.dismantle(entity, world, target);
    }

}
