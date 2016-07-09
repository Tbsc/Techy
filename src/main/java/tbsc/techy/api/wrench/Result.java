package tbsc.techy.api.wrench;

/**
 * This enum is used to know the result of the rotation/dismantle.
 * Although you can check if the item can rotate, you can't know if the operation succeeded.
 * This enum can be used to know if the operation was successful and change what happens
 * based on that.
 */
public enum Result {

    SUCCESS, FAIL

}
