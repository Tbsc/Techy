package tbsc.techy.api;

import net.minecraft.util.ITickable;

/**
 * This API interface declares whether a class does any kind of process (in-game), and
 * provides methods for that.
 * It extends {@link ITickable} as the operation needs to run every tick.
 *
 * if (canOperate()) {
 *    doOperation();
 *    setOperationStatus(true);
 * } else {
 *    setOperationStatus(false);
 * }
 *
 * This snippet of code shows how operations should be done.
 *
 * Created by tbsc on 3/29/16.
 */
public interface IOperator extends ITickable {

    /**
     * This class *should* do {@code canOperate()} before working to make sure it will
     * work.
     */
    void doOperation();

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
     * This method is different from {@code canOperate()} in a few ways.
     * 1. {@code canOperate()} *just* checks if it can. {@code shouldOperate()} checks
     *    if it should operate. This is useful in the case of redstone control, as
     *    technically it *can* run, but shouldn't.
     * 2. Adds more code flexibility.
     *
     * @return if it should operate
     */
    boolean shouldOperate();

    /**
     *
     *
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
