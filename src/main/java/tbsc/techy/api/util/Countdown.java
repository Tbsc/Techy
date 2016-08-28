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

package tbsc.techy.api.util;

/**
 * Utility class for creating countdowns. Creation of multiple instances of this class is not encouraged, but
 * rather resetting the countdown and changing the total tick count, to save memory.
 * Unless absolutely needed, for instance where multiple
 *
 * Created by tbsc on 25/07/2016.
 */
public class Countdown {

    private int tickCount;
    private int totalTicks;

    /**
     * Default constructor for setting both the current tick count and total tick amount to
     * zero, mostly used for storing an instance of this class for future use.
     */
    public Countdown() {
        this(0);
    }

    /**
     * Constructor for setting total amount of ticks, and the current tick count to zero.
     * @param totalTicks Total tick amount
     */
    public Countdown(int totalTicks) {
        this(0, totalTicks);
    }

    /**
     * Constructor for setting the current tick amount and total ticks.
     * @param currentTicks Current tick count
     * @param totalTicks Total tick amount
     */
    public Countdown(int currentTicks, int totalTicks) {
        this.tickCount = currentTicks;
        this.totalTicks = totalTicks;
    }

    /**
     * This method is what runs the countdown.
     * The place that uses this countdown has to call this every tick, otherwise the countdown
     * can't count.
     */
    public void tick() {
        ++this.tickCount;
    }

    /**
     * Checks if the countdown has finished.
     * @return {@link #tickCount} equals or bigger than {@link #totalTicks}
     */
    public boolean hasFinished() {
        return tickCount >= totalTicks;
    }

    /**
     * Resets the counter to zero.
     */
    public void reset() {
        this.tickCount = 0;
    }

    /**
     * Resets the counter to zero, and changes the total tick amount.
     * @param totalTicks New total tick amount
     */
    public void reset(int totalTicks) {
        this.tickCount = 0;
        this.totalTicks = 0;
    }

    /**
     * @return Current tick count
     */
    public int getTickCount() {
        return tickCount;
    }

    /**
     * @return Total tick amount
     */
    public int getTotalTicks() {
        return totalTicks;
    }

    /**
     * Sets the current tick count
     * @param tickCount new tick count
     */
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    /**
     * Sets the total tick amount
     * @param totalTicks new total tick amount
     */
    public void setTotalTicks(int totalTicks) {
        this.totalTicks = totalTicks;
    }

}
