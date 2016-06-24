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

import net.minecraft.tileentity.TileEntity;

/**
 * To be implemented on blocks that hold a tile entity.
 *
 * Created by tbsc on 6/23/16.
 */
public interface IHasTileEntity {

    /**
     * Returns the tile entity class for this block.
     * @param <T> Type parameter for the tile entity class
     * @return The class of the tile entity
     */
    <T extends TileEntity> Class<T> getTileClass();

    /**
     * Get the identifier that will be registered for this tile class.
     * @return String tile identifier
     */
    String getTileID();

}
