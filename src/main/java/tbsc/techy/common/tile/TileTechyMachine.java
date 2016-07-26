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

package tbsc.techy.common.tile;

/**
 * Subclass of {@link TileTechyBase}, that adds more support for machine stuff,
 * such as processing and automation.
 * Most of those stuff need to be implemented manually, because capabilities
 * can't do those things.
 *
 * Created by tbsc on 13/07/2016.
 */
public class TileTechyMachine extends TileTechyBase {

    public TileTechyMachine(int invSize) {
        super(invSize);
    }

}
