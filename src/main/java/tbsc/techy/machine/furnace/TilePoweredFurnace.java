package tbsc.techy.machine.furnace;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
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
        
    }

    @Override
    public boolean canOperate() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true; // TODO Input slot should only accept items in the registry
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
