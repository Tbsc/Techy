package tbsc.techy.api.operation;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Basic interface for operations. Try not to implement this directly; use {@link OperatorBuilder} to
 * automatically create operation instructions based on defined settings.
 *
 * Created by tbsc on 24/07/2016.
 */
public interface IOperator extends INBTSerializable<NBTTagCompound> {

    /**
     * This method is the operation update method.
     * It automatically does everything that needs to be done every tick for the operation to work.
     */
    boolean updateOperation();

    /**
     * Finishes the operation. This will be called the the progress is finished.
     * @return Should mark dirty
     */
    boolean operate();

    /**
     * Checks if the machine can operate.
     * By operate I don't mean if it can **finish**, but rather if it can work at all.
     * Common things to check here are if an input is inserted AND is valid, machine can
     * output and preconditions are valid (enough energy).
     * @return Can operation run
     */
    boolean canOperate();

    /**
     * Checks if the machine should operate. This varies from {@link #canOperate()} in that its
     * not that the machine can't take the input and return an output stack, but rather something
     * is preventing it. Example is redstone preventing the machine from running.
     * @return Should operation run
     */
    boolean shouldOperate();

    /**
     * Get the total amount of time progressed in ticks.
     * @return Progress done
     */
    int getProgress();

    /**
     * The total amount of ticks that the progress needs to reach for the operation to finish.
     * @return Total tick progress
     */
    int getCurrentTotalProgress();

    /**
     * Implementation of {@link IOperator} that allows for modifying some values of the interface.
     */
    interface Modifiable extends IOperator {

        /**
         * Sets the current progress.
         * @param progress Progress done
         */
        void setProgress(int progress);

        /**
         * Sets the total amount of ticks to finish progress.
         * @param totalProgress Total tick amount
         */
        void setCurrentTotalProgress(int totalProgress);

    }

}
