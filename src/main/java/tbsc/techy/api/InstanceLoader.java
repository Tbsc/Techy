/*
 * Copyright © 2016 Tbsc
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Butter.  If not, see <http://www.gnu.org/licenses/>.
 */

package tbsc.techy.api;

/**
 * This runnable is intended to be used to load instances based on the interfaces they are implementing, without
 * having to hard code support for those interfaces.
 *
 * Created by tbsc on 04/07/2016.
 */
public interface InstanceLoader {

    /**
     * Lets the loader do whatever it wants with the instance, based on the type of
     * interface.
     * @param instance The instance to be applied to
     */
    void load(Object instance);

}
