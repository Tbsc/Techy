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

package tbsc.techy.api.loader;

import net.minecraft.item.ItemBlock;

/**
 * This interface should only be implemented on blocks (or nothing would happen), and is used to note
 * that this block class should also be registered to the game as an {@link ItemBlock}.
 * The {@link ItemBlock} that will be registered is gotten from {@link #getItemBlock()},
 *
 * Created by tbsc on 03/07/2016.
 */
public interface IHasItemBlock<B extends ItemBlock> {

    /**
     * Returns the {@link ItemBlock} class to be registered to the game.
     * Can be used to register advanced {@link ItemBlock}s without needing to register
     * one, just by letting the loader class do it.
     * @return The item block to register.
     */
    B getItemBlock();

}
