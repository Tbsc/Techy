package tbsc.techy.api.operation;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Empty operator. Used by the capability to define a default, that does nothing.
 * I do this simply because the default requires a tile entity, and I can't have one.
 * Instead I created a new class that will just do nothing.
 *
 * Created by tbsc on 8/28/16.
 */
public class EmptyOperator implements IOperator {

    @Override
    public boolean updateOperation() {
        return false;
    }

    @Override
    public boolean operate() {
        return false;
    }

    @Override
    public boolean canOperate() {
        return false;
    }

    @Override
    public boolean shouldOperate() {
        return false;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public int getCurrentTotalProgress() {
        return 0;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

}
