package tbsc.techy.api.wrench;

/**
 * This enum is used to know the result of the rotation/dismantle.
 * Although you can check if the item can rotate, you can't know if the operation succeeded.
 * This enum can be used to know if the operation was successful and change what happens
 * based on that.
 */
public enum Result {

    SUCCESS(true), FAIL(false);

    private final boolean value;

    Result(boolean value) {
        this.value = value;
    }

    /**
     * Basically, if succeeded, then true. If failed, false.
     * @return The value of the result.
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Has it succeeded.
     * @return Is the result positive
     */
    public boolean hasSucceeded() {
        return value;
    }

    /**
     * Has it failed.
     * @return Is the result negative.
     */
    public boolean hasFailed() {
        return !value;
    }

}
