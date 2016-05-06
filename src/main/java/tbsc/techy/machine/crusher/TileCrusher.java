package tbsc.techy.machine.crusher;

import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.recipe.CrusherRecipes;
import tbsc.techy.recipe.StackRecipeInput;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Random;

/**
 * Crusher's tile entity.
 *
 * Created by tbsc on 5/4/16.
 */
public class TileCrusher extends TileMachineBase {

    int totalProgress;
    int progress;
    public EnumMap<Sides, SideConfiguration> sideConfigMap = new EnumMap<>(Sides.class);

    public TileCrusher() {
        super(50000, 700, BlockCrusher.tileInvSize, ConfigData.crusherDefaultProceessTime);

        sideConfigMap.put(Sides.UP, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.DOWN, SideConfiguration.OUTPUT);
        sideConfigMap.put(Sides.FRONT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.BACK, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.LEFT, SideConfiguration.INPUT);
        sideConfigMap.put(Sides.RIGHT, SideConfiguration.INPUT);
    }

    @Override
    public void doOperation() {
        // Double checking, you can never check more than enough
        if (this.canOperate()) {
            ItemStack recipeOutput = getSmeltingOutput(inventory[0]);
            float experience = (experienceModifier / 100) * CrusherRecipes.instance().getSmeltingExperience(recipeOutput);

            if (this.inventory[1] == null) {
                this.inventory[1] = recipeOutput.copy();
            } else if (this.inventory[1].getItem() == recipeOutput.getItem()) {
                this.inventory[1].stackSize += recipeOutput.stackSize; // Forge BugFix: Results may have multiple items
                Random rand = new Random();
                ImmutableTriple<ItemStack, ItemStack, Integer> triple = CrusherRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0]));
                if (triple.getMiddle() != null) { // If there is any additional output
                    int chance = rand.nextInt(100) + 1;
                    if (chance <= triple.getRight()) {
                        // Finished with the checking, it found a random number and it matched, so extra item to be given
                        if (this.inventory[2] == null) {
                            this.inventory[2] = triple.getMiddle().copy();
                        } else if (this.inventory[2].getItem() == triple.getMiddle().getItem() && this.inventory[2].getMaxStackSize() >= this.inventory[2].stackSize + triple.getMiddle().stackSize) {
                            this.inventory[2].stackSize += triple.getMiddle().stackSize;
                        }
                    }
                }
                if (additionalItemModifier >= 1) {
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

    @Override
    public boolean canOperate() {
        // No input item
        if (inventory[0] == null) {
            return false;
        } else {
            // No recipe with input item
            if (CrusherRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0])) == null) {
                return false;
            }
            // There is a recipe, then store the output in a variable
            ItemStack recipeOutput = CrusherRecipes.instance().getSmeltingResult(StackRecipeInput.of(inventory[0])).getLeft();
            // Not enough energy stored in tile
            if ((energyModifier / 100) * CrusherRecipes.instance().getSmeltingEnergy(recipeOutput) >= getEnergyStored(EnumFacing.DOWN)) {
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

    @Nonnull
    @Override
    public int[] getEnergySlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[] {
                0
        };
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[] {
                1, 2
        };
    }

    @Nonnull
    @Override
    public int[] getBoosterSlots() {
        return new int[] {
                4, 5, 6, 7
        };
    }

    /**
     * Since it will only accept a single itemstack, I will just return the first output.
     * @param input input item stack
     * @return first output stack
     */
    @Override
    public ItemStack getSmeltingOutput(ItemStack input) {
        return CrusherRecipes.instance().getSmeltingResult(StackRecipeInput.of(input)) != null ? CrusherRecipes.instance().getSmeltingResult(StackRecipeInput.of(input)).getLeft() : null;
    }

    @Override
    public int getEnergyUsage(ItemStack output) {
        return CrusherRecipes.instance().getSmeltingEnergy(output);
    }

    @Override
    public int getOperationProgress() {
        return progress;
    }

    @Override
    public int getOperationTotalProgress() {
        return totalProgress;
    }

    @Override
    public SideConfiguration getConfigurationForSide(Sides side) {
        return sideConfigMap.get(side);
    }

    @Override
    public void setConfigurationForSide(Sides side, SideConfiguration sideConfig) {
        sideConfigMap.put(side, sideConfig);
    }

    @Override
    public int[] getSlotsForConfiguration(SideConfiguration sideConfig) {
        switch (sideConfig) {
            case INPUT:
                return getInputSlots();
            case OUTPUT:
                return getOutputSlots();
            case IO:
                return ArrayUtils.addAll(getInputSlots(), getOutputSlots());
            default:
                return new int[0];
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockCrusher.FACING);
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
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockCrusher.FACING);
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
        return sideAllows && ArrayUtils.contains(getInputSlots(), index);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing side) {
        EnumFacing frontOfBlock = worldObj.getBlockState(pos).getValue(BlockCrusher.FACING);
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

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index) {
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return EnergyHelper.isEnergyContainerItem(stack);
            case 4 | 5 | 6 | 7:
                return stack.getItem() instanceof IBoosterItem;
            default:
                return false;
        }
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return getEnergyStored(EnumFacing.DOWN);
            case 1:
                return progress;
            case 2:
                return totalProgress;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                setEnergyStored(value);
                break;
            case 1:
                progress = value;
                break;
            case 2:
                totalProgress = value;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public String getName() {
        return StatCollector.translateToLocal("tile.Techy:blockCrusher.name");
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(getName());
    }

}
