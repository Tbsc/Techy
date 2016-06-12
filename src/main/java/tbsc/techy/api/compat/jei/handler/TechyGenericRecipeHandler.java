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

package tbsc.techy.api.compat.jei.handler;

import mezz.jei.api.recipe.IRecipeHandler;
import tbsc.techy.api.compat.jei.wrapper.TechyRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Generic class for recipe handlers.
 *
 * Created by tbsc on 6/4/16.
 */
public abstract class TechyGenericRecipeHandler<T extends TechyRecipeWrapper> implements IRecipeHandler<T> {

    @Nonnull
    private final String id;

    public TechyGenericRecipeHandler(String id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return id;
    }

}
