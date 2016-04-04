package tbsc.techy.machine.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import tbsc.techy.recipe.MachineRecipes;
import tbsc.techy.tile.TileMachineBase;

/**
 * SLOT 0: Input slot
 * SLOT 1: Output slot
 *
 * Created by tbsc on 3/27/16.
 */
public class TilePoweredFurnace extends TileMachineBase {

    public TilePoweredFurnace() {
        super(30000, 2000, BlockPoweredFurnace.tileInvSize);
    }

    @Override
    public void doOperation() {
        ItemStack itemstack = MachineRecipes.getOutputStack(MachineRecipes.RecipeMachineType.POWERED_FURNACE, this.inventory[0]);

        if (this.inventory[1] == null) {
            this.inventory[1] = itemstack.copy();
        } else if (this.inventory[1].getItem() == itemstack.getItem()) {
            this.inventory[1].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
        }

        --this.inventory[0].stackSize;

        if (this.inventory[0].stackSize <= 0) {
            this.inventory[0] = null;
        }
    }

    @Override
    public boolean canOperate() {
        ItemStack input = inventory[0];
        ItemStack outputSlotStack = inventory[1];
        ItemStack recipeOutput = MachineRecipes.getOutputStack(MachineRecipes.RecipeMachineType.POWERED_FURNACE, input);
        if (input == null) {
            return false;
        } else {
            if (recipeOutput == null) {
                return false;
            }
            if (outputSlotStack == null || (outputSlotStack.isItemEqual(recipeOutput) && (outputSlotStack.stackSize + recipeOutput.stackSize <= outputSlotStack.stackSize || outputSlotStack.stackSize + recipeOutput.stackSize <= getInventoryStackLimit()))) {
                return true;
            }
            if (!outputSlotStack.isItemEqual(input)) {
                return false;
            }
            int result = outputSlotStack.stackSize + recipeOutput.stackSize;
            return result <= getInventoryStackLimit() && result <= outputSlotStack.getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 0) {
            return MachineRecipes.getOutputStack(MachineRecipes.RecipeMachineType.POWERED_FURNACE, stack) != null;
        }
        return index != 1;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public String getName() {
        return StatCollector.translateToLocal("tile.Techy:blockPoweredFurnace.name");
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentText(StatCollector.translateToLocal("tile.Techy:blockPoweredFurnace.name"));
    }
}
