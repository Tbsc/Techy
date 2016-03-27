package tbsc.techy.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;

/**
 * Created by tbsc on 3/27/16.
 */
public class ItemBase extends Item {

    public ItemBase(String unlocalizedName) {
        setCreativeTab(Techy.tabTechyItems);
        setUnlocalizedName(Techy.MODID + ":" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
