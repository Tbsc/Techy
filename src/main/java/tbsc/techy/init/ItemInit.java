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

    public static void init() {
        itemWrench = new ItemWrench();
        itemBoosterEnergy = new ItemBoosterEnergy();
        itemBoosterTime = new ItemBoosterTime();
        itemBoosterBonus = new ItemBoosterAdditionalItems();

        GameRegistry.registerItem(itemWrench);
        GameRegistry.registerItem(itemBoosterEnergy);
        GameRegistry.registerItem(itemBoosterTime);
        GameRegistry.registerItem(itemBoosterBonus);
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
    }

}
