package tbsc.techy.machine.furnace;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import tbsc.techy.ConfigData;
import tbsc.techy.api.IBoosterItem;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.init.BlockInit;
import tbsc.techy.recipe.PoweredFurnaceRecipes;
import tbsc.techy.recipe.StackRecipeInput;
import tbsc.techy.tile.TileMachineBase;

import javax.annotation.Nonnull;
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
public class TilePoweredFurnace extends TileMachineBase implements IEnergyReceiver {

    public TilePoweredFurnace() {
        super(40000, 640, BlockPoweredFurnace.tileInvSize, ConfigData.furnaceDefaultCookTime);

        setConfigurationForSide(Sides.UP, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.DOWN, SideConfiguration.OUTPUT);
        setConfigurationForSide(Sides.FRONT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.BACK, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.LEFT, SideConfiguration.INPUT);
        setConfigurationForSide(Sides.RIGHT, SideConfiguration.INPUT);
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
    public ITextComponent getDisplayName() {
        return new TextComponentString(BlockInit.blockPoweredFurnace.getLocalizedName());
    }

}
