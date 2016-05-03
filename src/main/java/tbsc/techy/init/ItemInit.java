package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.item.ItemBase;
import tbsc.techy.item.ItemBoosterBase;
import tbsc.techy.item.ItemBoosterEnergy;
import tbsc.techy.item.ItemWrench;

/**
 * Loads and contains all of Techy's items.
 *
 * Created by tbsc on 3/26/16.
 */
public class ItemInit {

    public static ItemBase itemWrench;
    public static ItemBoosterBase itemBoosterEnergy;

    public static void init() {
        itemWrench = new ItemWrench();
        itemBoosterEnergy = new ItemBoosterEnergy();

        GameRegistry.registerItem(itemWrench);
        GameRegistry.registerItem(itemBoosterEnergy);
    }

    /**
     * Loads item models.
     * Item's need to be added here manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemWrench.initModel();
        itemBoosterEnergy.initModel();
    }

}
