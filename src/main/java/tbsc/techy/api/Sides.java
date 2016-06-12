/*
 * Copyright © 2016 Tbsc
 *
 * Techy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Techy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Techy.  If not, see <http://www.gnu.org/licenses/>.
 */

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
