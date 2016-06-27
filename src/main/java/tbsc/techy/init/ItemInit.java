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

package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.item.*;
import tbsc.techy.item.ItemBatteryBase;
import tbsc.techy.item.ItemGenericCraftingComponent;

/**
 * Loads and contains all of Techy's items.
 *
 * Created by tbsc on 3/26/16.
 */
public class ItemInit {

    public static ItemBase itemWrench;
    public static ItemDusts itemDusts;
    public static ItemIngots itemIngots;
    public static ItemBoosterBase itemBoosterEnergy;
    public static ItemBoosterBase itemBoosterTime;
    public static ItemBoosterBase itemBoosterBonus;
    public static ItemBatteryBase itemBatterySmall;
    public static ItemBatteryBase itemBatteryMedium;
    public static ItemBatteryBase itemBatteryLarge;
    public static ItemGenericCraftingComponent itemHeatingComponent;
    public static ItemGenericCraftingComponent itemGrindingComponent;
    public static ItemGenericCraftingComponent itemIgnitionComponent;

    public static void init() {
        itemWrench = new ItemWrench();
        itemDusts = new ItemDusts();
        itemIngots = new ItemIngots();
        itemHeatingComponent = new ItemGenericCraftingComponent("itemHeatingComponent");
        itemGrindingComponent = new ItemGenericCraftingComponent("itemGrindingComponent");
        itemIgnitionComponent = new ItemGenericCraftingComponent("itemIgnitionComponent");
        itemBoosterEnergy = new ItemBoosterEnergy();
        itemBoosterTime = new ItemBoosterTime();
        itemBoosterBonus = new ItemBoosterAdditionalItems();
        itemBatterySmall = new ItemBatteryBase("itemBatterySmall", 100000, 500, 500);
        itemBatteryMedium = new ItemBatteryBase("itemBatteryMedium", 1000000, 1000, 1000);
        itemBatteryLarge = new ItemBatteryBase("itemBatteryLarge", 10000000, 2500, 2500);

        GameRegistry.register(itemWrench);
        GameRegistry.register(itemDusts);
        GameRegistry.register(itemIngots);
        GameRegistry.register(itemBoosterEnergy);
        GameRegistry.register(itemBoosterTime);
        GameRegistry.register(itemBoosterBonus);
        GameRegistry.register(itemBatterySmall);
        GameRegistry.register(itemBatteryMedium);
        GameRegistry.register(itemBatteryLarge);
        GameRegistry.register(itemHeatingComponent);
        GameRegistry.register(itemGrindingComponent);
        GameRegistry.register(itemIgnitionComponent);
    }

    /**
     * Loads item models.
     * Item's need to be added here manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemWrench.initModel();
        itemDusts.initModel();
        itemIngots.initModel();
        itemBoosterEnergy.initModel();
        itemBoosterTime.initModel();
        itemBoosterBonus.initModel();
        itemBatterySmall.initModel();
        itemBatteryMedium.initModel();
        itemBatteryLarge.initModel();
        itemHeatingComponent.initModel();
        itemGrindingComponent.initModel();
        itemIgnitionComponent.initModel();
    }

}
