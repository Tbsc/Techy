package tbsc.techy.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.api.wrench.ITechyDismantleable;
import tbsc.techy.api.wrench.ITechyWrench;
import tbsc.techy.api.wrench.Result;
import tbsc.techy.common.loader.annotation.Register;

/**
 * Created by tbsc on 10/07/2016.
 */
public class ItemWrench extends ItemBase implements ITechyWrench {

    @Register.Instance
    public static ItemWrench instance = new ItemWrench();

    /**
     * Constructor for the item wrench.
     */
    public ItemWrench() {
        super("itemWrench");
    }

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
        return world.getBlockState(target) == Blocks.AIR.getDefaultState() && ArrayUtils.contains(entity.worldObj.getBlockState(target).getBlock().getValidRotations(entity.worldObj, target), rotateTo);
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
        return entity.worldObj.getBlockState(target) != Blocks.AIR.getDefaultState() && entity.worldObj.getBlockState(target).getBlock() instanceof ITechyDismantleable;
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
        ITechyDismantleable dismantle = (ITechyDismantleable) world.getBlockState(target).getBlock();
        return dismantle.dismantle(entity, world, target, false).getResult();
    }

}
