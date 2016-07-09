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

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

/**
 * Used to mark blocks with a tile entity, for the butter loader to register it.
 * Extends ITileEntityProvider to automatically let Minecraft know block that
 * implement this interface are tile entities.
 * Uses type parameter {@link T} for the tile entity.
 *
 * Created by tbsc on 04/07/2016.
 */
public interface IHasTileEntity<T extends TileEntity> extends ITileEntityProvider {

    /**
     * Used to register the tile entity to the game.
     * @return The class of the tile entity
     */
    Class<T> getTileClass();

    /**
     * Returns the identifier for the tile entity.
     * @return Tile identifier
     */
    String getTileIdentifier();

}
