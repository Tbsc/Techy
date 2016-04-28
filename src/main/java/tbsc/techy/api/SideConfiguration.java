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
