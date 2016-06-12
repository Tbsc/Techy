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

package tbsc.techy.item;

/**
 * Basic crafting component.
 * Since most crafting components are just normal items with a texture, but
 * have no real functionality other than being used as a crafting ingredient
 * or used in another machine. Therefore 1 class that can be reused with
 * different unlocalized names is good.
 *
 * Created by tbsc on 5/5/16.
 */
public class ItemGenericCraftingComponent extends ItemBase {

    public ItemGenericCraftingComponent(String unlocalizedName) {
        super(unlocalizedName);
    }

}
