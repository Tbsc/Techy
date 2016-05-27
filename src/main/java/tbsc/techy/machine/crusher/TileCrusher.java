package tbsc.techy.machine.crusher;

import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.recipe.CrusherRecipes;
import tbsc.techy.recipe.StackRecipeInput;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Crusher's tile entity.
 *
 * Created by tbsc on 5/4/16.
 */
public class TileCrusher extends TileMachineBase implements IEnergyReceiver {

    public TileCrusher() {
        super(50000, 700, BlockCrusher.tileInvSize, ConfigData.crusherDefaultProceessTime);

        setConfigurationForSide(Sides.UP, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.DOWN, SideConfiguration.OUTPUT);
        setConfigurationForSide(Sides.FRONT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.BACK, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.LEFT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.RIGHT, SideConfiguration.INPUT);
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
            if ((energyModifier / 100) * CrusherRecipes.instance().getSmeltingEnergy(recipeOutput) >= getEnergyStored()) {
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

    /**
     * Receives energy and marks the block for update
     * <p>
     * All documentation from now on is made by Team CoFH.
     *
     * @param from       Orientation the energy is received from.
     * @param maxReceive Maximum amount of energy to receive.
     * @param simulate   If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        markDirty();
        return energyStorage.receiveEnergy(maxReceive, simulate);
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
    public String getName() {
        return I18n.translateToLocal("tile.Techy:blockCrusher.name");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(getName());
    }

}
