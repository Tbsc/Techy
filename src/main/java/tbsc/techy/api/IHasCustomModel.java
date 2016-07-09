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

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * To be implemented on any block or item that has a model. The loader will detect the interface and
 * call {@link #loadCustomModel()};
 * Since models can be only loaded from the client side, this interface is annotated with {@link SideOnly},
 * to make sure it only runs on the client
 *
 * Created by tbsc on 03/07/2016.
 */
@SideOnly(Side.CLIENT)
public interface IHasCustomModel {

    /**
     * Everything needed to load the model of the block or item needs to be done from here.
     * Obviously calling other methods to do this is fine, just do it from here and from the
     * constructor or something.
     */
    void loadCustomModel();

}
