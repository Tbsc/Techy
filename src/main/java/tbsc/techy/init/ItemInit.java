package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.item.ItemBase;
import tbsc.techy.item.ItemWrench;

/**
 * Created by tbsc on 3/26/16.
 */
public class ItemInit {

    public static ItemBase itemWrench;

    public static void init() {
        itemWrench = new ItemWrench();

        GameRegistry.registerItem(itemWrench);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemWrench.initModel();
    }

}
