package tbsc.techy.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.Techy;

/**
 * Basic item class.
 * Contains basic model rendering, creative tab and names.
 *
 * Created by tbsc on 3/27/16.
 */
public abstract class ItemBase extends Item {

    protected ItemBase(String unlocalizedName) {
        setCreativeTab(Techy.tabTechyItems);
        setRegistryName(unlocalizedName);
        setUnlocalizedName(Techy.MODID + ":" + unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
