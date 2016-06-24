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

package tbsc.techy.api.register;

/**
 * In order for the registered class to be registered correctly, the class
 * needs to implement this so it has basic methods the registrar needs. for
 * example {@link #initModel()}
 *
 * Created by tbsc on 6/24/16.
 */
public interface ITechyRegister {

    /**
     * Everything related to render of the block needs to be done in this method, for
     * example setting models based on metadata and other stuff.
     */
    void initModel();

}
