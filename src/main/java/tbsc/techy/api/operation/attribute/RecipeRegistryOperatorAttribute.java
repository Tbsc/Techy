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

package tbsc.techy.api.operation.attribute;

import tbsc.techy.api.operation.OperatorBuilder;
import tbsc.techy.api.registry.recipe.IRecipeRegistry;

/**
 * Created by tbsc on 8/28/16.
 */
public class RecipeRegistryOperatorAttribute implements IOperatorAttribute {

    private OperatorBuilder builder;
    public IRecipeRegistry registry;

    public RecipeRegistryOperatorAttribute(OperatorBuilder builder) {
        this.builder = builder;
    }

    /**
     * Defines the registry for this operator
     * @param registry The recipe registry
     * @return This object
     */
    public RecipeRegistryOperatorAttribute setRecipeRegistry(IRecipeRegistry registry) {
        this.registry = registry;
        return this;
    }

    /**
     * Return to the builder
     * @return parent builder
     */
    @Override
    public OperatorBuilder finish() {
        return builder;
    }

}
