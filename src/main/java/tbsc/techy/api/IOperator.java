package tbsc.techy.api;

import net.minecraft.util.ITickable;

/**
 * This API interface declares whether a class does any kind of process (in-game), and
 * provides methods for that.
 * It extends {@link ITickable} as the operation needs to run every tick.
 *
 * if (canOperate() && shouldOperate()) {
 *    doOperation();
 *    setOperationStatus(true);
 * } else {
 *    setOperationStatus(false);
 * }
 *
 * This code shows how to use this interface, in the most basic way.
 *
 * Created by tbsc on 3/29/16.
 */
public interface IOperator extends ITickable {

    /**
     * This class *should* do {@link #canOperate()} before working to make sure it will
     * work.
     * In implementations, this method typically will remove a bit from the input and add the output
     * to the output slot. It sometimes will also spawn experience.
     * Energy consumption typically is done every tick through the {@link ITickable} update() method,
     * however it is optional.
     */
    void doOperation();

    /**
     * In order to stop the current progress and operation of the machine, there needs to be
     * a method for that,
     * What this method should do is reset the progress, and change the operation status to false,
     * so just make it stop working.
     * Because of the way operations are built, 1 tick after you stop the operation it should start
     * it again (if it can operate and should operate), as it should just stop the operation, not
     * prevent it from operating. For that, there is the {@link #shouldOperate()} and the
     * {@link #setShouldOperate(boolean)} methods.
     */
    void stopOperating();

    /**
     * Checks in the class if it can operate, and returns that.
     * Usually, it would check if there is enough space/power/resources.
     * It (typically) should *NOT* return false if an operation is running, as
     * this method needs to return true for the operation to work (as the
     * operation runs every tick, and therefore checks if it can work *every
     * tick*).
     * It can also use a field
     *
     * @return whether an operation can happen
     */
    boolean canOperate();

    /**
     * This method is different from {@link #canOperate()} in a few ways.
     * 1. {@link #canOperate()} *just* checks if it can. This methods should check
     *    if it should operate. This is useful in the case of redstone control, as
     *    technically it *can* run, but shouldn't.
     * 2. Adds more code flexibility.
     *
     * @return if it should operate
     */
    boolean shouldOperate();

    /**
     * Operations have progress, and this method returns the progress the machine has done.
     * @return progress (starting from 0) of operation
     */
    int getOperationProgress();

    /**
     * The total amount of time in ticks this operation should take.
     * @return current operation total time
     */
    int getOperationTotalProgress();

    /**
     * @param isRunning the new status
     */
    void setOperationStatus(boolean isRunning);

    /**
     * Very simple. Usually just needs to return the value of a field
     * that stores that.
     *
     * @return whether an operation is running
     */
    boolean isOperating();

}
