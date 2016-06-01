package tbsc.techy.api;

import net.minecraft.util.EnumFacing;

/**
 * Declares the status of a side, using a few enums.
 * <p>
 * Created by tbsc on 4/26/16.
 */
public enum Sides {

    FRONT(2), BACK(3), DOWN(EnumFacing.DOWN.ordinal()), UP(EnumFacing.UP.ordinal()), LEFT(4), RIGHT(5), UNKNOWN(9001);

    public int side;

    Sides(int side) {
        this.side = side;
    }

    public static Sides fromOrdinal(int ordinal) {
        if (ordinal == FRONT.ordinal()) return FRONT;
        if (ordinal == BACK.ordinal()) return BACK;
        if (ordinal == DOWN.ordinal()) return DOWN;
        if (ordinal == UP.ordinal()) return UP;
        if (ordinal == LEFT.ordinal()) return LEFT;
        if (ordinal == RIGHT.ordinal()) return RIGHT;
        return UNKNOWN;
    }

}
