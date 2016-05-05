package tbsc.techy.machine.furnace;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ArrayUtils;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.init.BlockInit;
import tbsc.techy.recipe.PoweredFurnaceRecipes;
import tbsc.techy.recipe.StackRecipeInput;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Random;

/**
 * Slot 0 - Input
 * Slot 1 - Output
 * Slot 2 - Booster slot
 * <p>
 * Packet field 1 - Energy stored
 * Packet field 2 - Progress
 * Packet field 3 - Max Progress
 * <p>
 * Created by tbsc on 4/22/16.
 */
public class TilePoweredFurnace extends TileMachineBase {

    /**
     * Contains values of configurations for sides.
     * Since it is an {@link EnumMap}, it can *NOT* contain more than 1 enum
     * value per key, therefore there are always exactly 6 keys, the 6 sides.
     */
    public EnumMap<Sides, SideConfiguration> sideConfigMap = new EnumMap<>(Sides.class);

    public TilePoweredFurnace() {
        super(40000, 640, BlockPoweredFurnace.tileInvSize, ConfigData.furnaceDefaultCookTime);

        sideConfigMap.put(Sides.FRONT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.BACK, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.LEFT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.RIGHT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.UP, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.DOWN, SideConfiguration.OUTPUT);
    }

    /**
     * When executed, will do whatever that needs to be done in the tile.
     * In this case, smelting.
     */
    @Override
    public void doOperation() {
        // Double checking, you can never check more than enough
        if (this.canOperate()) {
            ItemStack recipeOutput = PoweredFurnaceRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0]));
            float experience = (experienceModifier / 100) * PoweredFurnaceRecipes.instance().getSmeltingExperience(recipeOutput);

            if (this.inventory[1] == null) {
                this.inventory[1] = recipeOutput.copy();
            } else if (this.inventory[1].getItem() == recipeOutput.getItem()) {
                this.inventory[1].stackSize += recipeOutput.stackSize; // Forge BugFix: Results may have multiple items
                if (additionalItemModifier >= 1) {
                    Random rand = new Random();
                    int randomInt = rand.nextInt((16 * additionalItemModifier) + (additionalItemModifier * 4)) + 1;
                    boolean matchFound = false;
                    // I couldn't find a better way to do this, so if you know one, let me know!
                    switch (additionalItemModifier) {
                        case 1:
                            if (randomInt == 1) {
                                matchFound = true;
                            }
                            break;
                        case 2:
                            if (randomInt == 1) {
                                matchFound = true;
                            }
                            if (randomInt == 2) {
                                matchFound = true;
                            }
                            break;
                        case 3:
                            if (randomInt == 1) {
                                matchFound = true;
                            }
                            if (randomInt == 2) {
                                matchFound = true;
                            }
                            if (randomInt == 3) {
                                matchFound = true;
                            }
                            break;
                        case 4:
                            if (randomInt == 1) {
                                matchFound = true;
                            }
                            if (randomInt == 2) {
                                matchFound = true;
                            }
                            if (randomInt == 3) {
                                matchFound = true;
                            }
                            if (randomInt == 4) {
                                matchFound = true;
                            }

                            break;
                    }
                    if (matchFound) {
                        int result = inventory[1].stackSize + additionalItemModifier;
                        if (result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize()) {
                            inventory[1].stackSize =+ additionalItemModifier;
                        }
                    }
                }
            }

            spawnXPOrb((int) experience, recipeOutput.stackSize);

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
    }

    /**
     * Checks if the tile can operate
     *
     * @return can the tile operate
     */
    @Override
    public boolean canOperate() {
        // No input item
        if (inventory[0] == null) {
            return false;
        } else {
            // No recipe with input item
            if (PoweredFurnaceRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0])) == null) {
                return false;
            }
            // There is a recipe, then store the output in a variable
            ItemStack recipeOutput = PoweredFurnaceRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0]));
            // Not enough energy stored in tile
            if ((energyModifier / 100) * PoweredFurnaceRecipes.instance().getSmeltingEnergy(recipeOutput) >= getEnergyStored(EnumFacing.DOWN)) {
                return false;
            }
            // If there is no item in output slot then it can smelt, returns true
            if (inventory[1] == null) {
                return true;
            }
            // If there is an item in output slot, then check if is the output of the current recipe
            if (!inventory[1].isItemEqual(recipeOutput)) {
                return false;
            }
            // Variable of the new output slot stack size
            int result = inventory[1].stackSize + recipeOutput.stackSize;
            // Checks if the new output slot stack size respects inventory stack limit and item stack limit
            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setInteger("TotalProgress", totalProgress);

        nbt.setInteger("SideConfigFront", getConfigurationForSide(Sides.FRONT).ordinal());
        nbt.setInteger("SideConfigBack", getConfigurationForSide(Sides.BACK).ordinal());
        nbt.setInteger("SideConfigLeft", getConfigurationForSide(Sides.LEFT).ordinal());
        nbt.setInteger("SideConfigRight", getConfigurationForSide(Sides.RIGHT).ordinal());
        nbt.setInteger("SideConfigUp", getConfigurationForSide(Sides.UP).ordinal());
        nbt.setInteger("SideConfigDown", getConfigurationForSide(Sides.DOWN).ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        totalProgress = nbt.getInteger("TotalProgress");

        setConfigurationForSide(Sides.FRONT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigFront")));
        setConfigurationForSide(Sides.BACK, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigBack")));
        setConfigurationForSide(Sides.LEFT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigLeft")));
        setConfigurationForSide(Sides.RIGHT, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigRight")));
        setConfigurationForSide(Sides.UP, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigUp")));
        setConfigurationForSide(Sides.DOWN, SideConfiguration.fromOrdinal(nbt.getInteger("SideConfigDown")));
    }

    /**
     * Returns the side config for the side given.
     * @param side the side to check
     * @return config for the side
     */
    @Override
    public SideConfiguration getConfigurationForSide(Sides side) {
        return sideConfigMap.get(side);
    }

    /**
     * Sets in the class the side config for the side given
     * @param side to change
     * @param sideConfig what to change
     */
    @Override
    public void setConfigurationForSide(Sides side, SideConfiguration sideConfig) {
        sideConfigMap.put(side, sideConfig);
    }

    /**
     * For the configuration given, return the slots this configuration should access
     * @param sideConfig configuration for side
     * @return slots for configuration
     */
    @Override
    public int[] getSlotsForConfiguration(SideConfiguration sideConfig) {
        switch (sideConfig) {
            case DISABLED:
                return new int[0];
            case INPUT:
                return getInputSlots();
            case OUTPUT:
                return getOutputSlots();
            case IO:
                return ArrayUtils.addAll(getInputSlots(), getOutputSlots());
            case UNKNOWN:
                return null;
            default:
                return null;
        }
    }

    /**
     * Checks if the player can use the tile entity.
     * @param player The player that's checked
     * @return if the player is in range of 8 blocks from the block.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(pos) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }

    /**
     * Not used right now, will be used sometime later (I don't know for what)
     * @return
     */
    public static ResourceLocation getMachineFrontTexture() {
        return new ResourceLocation("Techy:textures/blocks/blockPoweredFurnaceFront.png");
    }

    @Override
    public int getOperationProgress() {
        return progress;
    }

    @Override
    public int getOperationTotalProgress() {
        return totalProgress;
    }

    /**
     * Used for server/client communication, for transferring int fields between sides.
     *
     * @param id ID of field to get
     * @return the value for that field, unused right now
     */
    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return getEnergyStored(EnumFacing.DOWN);
            case 1:
                return getOperationProgress();
            case 2:
                return getOperationTotalProgress();
        }
        return 0;
    }

    /**
     * Sets the value of the specified field ID to the value specified
     *
     * @param id    of field
     * @param value of field
     */
    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.setEnergyStored(value);
                break;
            case 1:
                progress = value;
                break;
            case 2:
                totalProgress = value;
                break;
        }
    }

    /**
     * Returns how many field there are
     *
     * @return amount of fields
     */
    @Override
    public int getFieldCount() {
        return 0;
    }

    /**
     * Returns the slot ID array of the energy container slot
     * It needs to return an array in case there is no energy slot, therefore it should
     * return an empty array.
     *
     * @return energy container slot ID array
     */
    @Nonnull
    @Override
    public int[] getEnergySlots() {
        return new int[]{
                2
        };
    }

    /**
     * Returns the ID(s) of the input slot(s). If none, then return an empty int array.
     *
     * @return input slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[]{
                0
        };
    }

    /**
     * Returns the ID(s) of the output slot(s). If none, then return an empty int array.
     *
     * @return output slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[]{
                1
        };
    }

    /**
     * Booster items are upgrades for the machine.
     * This method should return an array of the slot IDs in which you should
     *
     * @return booster slot IDs array (can be empty, but not null!)
     */
    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[] {
            3, 4, 5, 6
        };
    }

    @Override
    public ItemStack getSmeltingOutput(ItemStack input) {
        return PoweredFurnaceRecipes.instance().getSmeltingResult(StackRecipeInput.of(input));
    }

    @Override
    public int getEnergyUsage(ItemStack output) {
        return PoweredFurnaceRecipes.instance().getSmeltingEnergy(output);
    }

    /**
     * If slot 0 (input) --> Check if {@code stack} is a valid input
     * If slot 1 (output) --> nothing can be inserted manually
     *
     * @param index slot
     * @param stack itemstack
     * @return if it can be there
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index) {
            case 0:
                return PoweredFurnaceRecipes.instance().getSmeltingResult(StackRecipeInput.of(stack)) != null;
            case 1:
                return false;
            case 2:
                return stack.getItem() instanceof IEnergyContainerItem;
            case 3 | 4 | 5 | 6:
                return stack.getItem() instanceof IBoosterItem;
        }
        return false;
    }

    /**
     * Returns the name of the TileEntity.
     *
     * @return name of the tile
     */
    @Override
    public String getName() {
        return BlockInit.blockPoweredFurnace.getLocalizedName();
    }

    /**
     * Returns the name of the tile entity, just in a chat supported format.
     *
     * @return name of the tile
     */
    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(BlockInit.blockPoweredFurnace.getLocalizedName());
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        if (frontOfBlock == side) { // FRONT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.FRONT));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.LEFT));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            return getSlotsForConfiguration(getConfigurationForSide(Sides.BACK));
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            return getSlotsForConfiguration(getConfigurationForSide(Sides.RIGHT));
        }
        if (side == EnumFacing.UP) { // UP
            return getSlotsForConfiguration(getConfigurationForSide(Sides.UP));
        }
        if (side == EnumFacing.DOWN) { // DOWN
            return getSlotsForConfiguration(getConfigurationForSide(Sides.DOWN));
        }
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        boolean sideAllows = false;
        if (frontOfBlock == side) { // FRONT
            sideAllows = getConfigurationForSide(Sides.FRONT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            sideAllows = getConfigurationForSide(Sides.LEFT).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            sideAllows = getConfigurationForSide(Sides.BACK).allowsInput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            sideAllows = getConfigurationForSide(Sides.RIGHT).allowsInput();
        }
        if (side == EnumFacing.UP) { // UP
            sideAllows = getConfigurationForSide(Sides.UP).allowsInput();
        }
        if (side == EnumFacing.DOWN) { // DOWN
            sideAllows = getConfigurationForSide(Sides.DOWN).allowsInput();
        }
        return sideAllows && ArrayUtils.contains(getOutputSlots(), index);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockPoweredFurnace.FACING);
        boolean sideAllows = false;
        if (frontOfBlock == side) { // FRONT
            sideAllows = getConfigurationForSide(Sides.FRONT).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // LEFT
            sideAllows = getConfigurationForSide(Sides.LEFT).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // BACK
            sideAllows = getConfigurationForSide(Sides.BACK).allowsOutput();
        }
        frontOfBlock = frontOfBlock.rotateAround(EnumFacing.Axis.Y);
        if (frontOfBlock == side) { // RIGHT
            sideAllows = getConfigurationForSide(Sides.RIGHT).allowsOutput();
        }
        if (side == EnumFacing.UP) { // UP
            sideAllows = getConfigurationForSide(Sides.UP).allowsOutput();
        }
        if (side == EnumFacing.DOWN) { // DOWN
            sideAllows = getConfigurationForSide(Sides.DOWN).allowsOutput();
        }
        return sideAllows && ArrayUtils.contains(getOutputSlots(), index);
    }
}
