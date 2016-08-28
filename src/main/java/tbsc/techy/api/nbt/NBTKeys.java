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

package tbsc.techy.api.nbt;

/**
 * This class holds many names of NBT tags in Techy, for external mods to use.
 * For example the current progress will be an integer called "Progress", and
 * the max progress will be called "TotalProgress".
 * Key names will be sorted internally based on the thing that holds it. For
 * example, tile entity key names are in the {@link Tile} subclass.
 *
 * Created by tbsc on 8/2/16.
 */
public final class NBTKeys {

    /**
     * Internal class for tile entity key names.
     */
    public static final class Tile {

        /**
         * Key name for machine progress done.
         */
        public static final String PROGRESS = "Progress";

        /**
         * Key name for total ticks a machine needs to do to complete an
         * operation.
         */
        public static final String TOTAL_PROGRESS = "TotalProgress";

    }

}
