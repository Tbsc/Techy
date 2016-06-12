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

/**
 * Contains data needed to configure sides.
 *
 * Created by tbsc on 4/27/16.
 */
public enum SideConfiguration {

    DISABLED, INPUT, OUTPUT, IO, UNKNOWN;

    public SideConfiguration cycleForward() {
        switch (this) {
            case DISABLED:
                return INPUT;
            case INPUT:
                return OUTPUT;
            case OUTPUT:
                return IO;
            case IO:
                return DISABLED;
            default:
                return UNKNOWN;
        }
    }

    public SideConfiguration cycleBackward() {
        switch (this) {
            case DISABLED:
                return IO;
            case INPUT:
                return DISABLED;
            case OUTPUT:
                return INPUT;
            case IO:
                return OUTPUT;
            default:
                return UNKNOWN;
        }
    }

    public static SideConfiguration fromString(String value) {
        if (value.equals(DISABLED.name())) return DISABLED;
        if (value.equals(INPUT.name())) return INPUT;
        if (value.equals(OUTPUT.name())) return OUTPUT;
        if (value.equals(IO.name())) return IO;
        return UNKNOWN;
    }

    public static SideConfiguration fromOrdinal(int ordinal) {
        if (ordinal == DISABLED.ordinal()) return DISABLED;
        if (ordinal == INPUT.ordinal()) return INPUT;
        if (ordinal == OUTPUT.ordinal()) return OUTPUT;
        if (ordinal == IO.ordinal()) return IO;
        if (ordinal == UNKNOWN.ordinal()) return UNKNOWN;
        return UNKNOWN;
    }

    public String toString() {
        switch (this) {
            case DISABLED:
                return "Disabled";
            case INPUT:
                return "Input";
            case OUTPUT:
                return "Output";
            case IO:
                return "Input/Output";
            default:
                return "Unknown?";
        }
    }

    public boolean allowsInput() {
        return this == INPUT || this == IO;
    }

    public boolean allowsOnlyInput() {
        return this == INPUT;
    }

    public boolean allowsOutput() {
        return this == OUTPUT || this == IO;
    }

    public boolean allowsOnlyOutput() {
        return this == OUTPUT;
    }

    public boolean allowsBoth() {
        return this == IO;
    }

    public boolean allowsNone() {
        return this == DISABLED;
    }

}
