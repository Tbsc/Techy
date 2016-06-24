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

package tbsc.techy.item.battery;

import tbsc.techy.api.register.RegisterInstance;
import tbsc.techy.api.register.TechyRegister;

/**
 * Class for the large battery item
 *
 * Created by tbsc on 6/24/16.
 */
public class ItemBatteryLarge extends ItemBatteryBase {

    public static final String IDENTIFIER = "itemBatteryLarge";

    @RegisterInstance(identifier = IDENTIFIER, registerClass = ItemBatteryLarge.class)
    public static ItemBatteryBase instance;

    @TechyRegister(identifier = IDENTIFIER)
    public ItemBatteryLarge() {
        super("itemBatteryLarge", 10000000, 2500, 2500);
    }

}
