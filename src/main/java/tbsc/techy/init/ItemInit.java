package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.item.*;

/**
 * Loads and contains all of Techy's items.
 *
 * Created by tbsc on 3/26/16.
 */
public class ItemInit {

    public static ItemBase itemWrench;
    public static ItemBoosterBase itemBoosterEnergy;
    public static ItemBoosterBase itemBoosterTime;
    public static ItemBoosterBase itemBoosterBonus;
    public static ItemBatteryBase itemBatterySmall;
    public static ItemBatteryBase itemBatteryMedium;
    public static ItemBatteryBase itemBatteryLarge;
    public static ItemDusts itemDusts;
    public static ItemGenericCraftingComponent itemHeatingComponent;
    public static ItemGenericCraftingComponent itemGrindingComponent;

    public static void init() {
        itemWrench = new ItemWrench();
        itemBoosterEnergy = new ItemBoosterEnergy();
        itemBoosterTime = new ItemBoosterTime();
        itemBoosterBonus = new ItemBoosterAdditionalItems();
        itemBatterySmall = new ItemBatteryBase("itemBatterySmall", 100000, 500, 500);
        itemBatteryMedium = new ItemBatteryBase("itemBatteryMedium", 1000000, 1000, 1000);
        itemBatteryLarge = new ItemBatteryBase("itemBatteryLarge", 10000000, 2500, 2500);
        itemDusts = new ItemDusts();
        itemHeatingComponent = new ItemGenericCraftingComponent("itemHeatingComponent");
        itemGrindingComponent = new ItemGenericCraftingComponent("itemGrindingComponent");

        GameRegistry.registerItem(itemWrench);
        GameRegistry.registerItem(itemBoosterEnergy);
        GameRegistry.registerItem(itemBoosterTime);
        GameRegistry.registerItem(itemBoosterBonus);
        GameRegistry.registerItem(itemBatterySmall);
        GameRegistry.registerItem(itemBatteryMedium);
        GameRegistry.registerItem(itemBatteryLarge);
        GameRegistry.registerItem(itemDusts);
        GameRegistry.registerItem(itemHeatingComponent);
        GameRegistry.registerItem(itemGrindingComponent);
    }

    /**
     * Loads item models.
     * Item's need to be added here manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemWrench.initModel();
        itemBoosterEnergy.initModel();
        itemBoosterTime.initModel();
        itemBoosterBonus.initModel();
        itemBatterySmall.initModel();
        itemBatteryMedium.initModel();
        itemBatteryLarge.initModel();
        itemDusts.initModel();
        itemHeatingComponent.initModel();
        itemGrindingComponent.initModel();
    }

}
