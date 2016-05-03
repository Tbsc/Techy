package tbsc.techy.item;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tbsc.techy.tile.TileMachineBase;

import java.util.List;

/**
 * Time reducer booster.
 *
 * Created by tbsc on 5/3/16.
 */
public class ItemBoosterTime extends ItemBoosterBase {

    public ItemBoosterTime() {
        super("itemBoosterTime");

        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(itemIn, 1, 1));
        subItems.add(new ItemStack(itemIn, 1, 2));
        subItems.add(new ItemStack(itemIn, 1, 3));
    }

    @Override
    public void initModel() {
        ModelBakery.registerItemVariants(this, new ResourceLocation(getRegistryName() + "T0"), new ResourceLocation(getRegistryName() + "T1"), new ResourceLocation(getRegistryName() + "T2"), new ResourceLocation(getRegistryName() + "T3"));
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "T0", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "T1", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(getRegistryName() + "T2", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 3, new ModelResourceLocation(getRegistryName() + "T3", "inventory"));
    }

    @Override
    public int getEnergyModifier(int tier) {
        switch (tier) {
            case 0:
                return 100;
            case 1:
                return 120;
            case 2:
                return 145;
            case 3:
                return 180;
            default:
                return 1;
        }
    }

    @Override
    public int getTimeModifier(int tier) {
        switch (tier) {
            case 0:
                return 125;
            case 1:
                return 85;
            case 2:
                return 60;
            case 3:
                return 35;
            default:
                return 100;
        }
    }

    @Override
    public int getExperienceModifier(int tier) {
        switch (tier) {
            case 0:
                return 1;
            case 1:
                return 90;
            case 2:
                return 75;
            case 3:
                return 45;
            default:
                return 1;
        }
    }

    @Override
    public int getAdditionalItemModifier(int tier) {
        return 0;
    }

    @Override
    public int[] getTiers() {
        return new int[] {
                0, 1, 2, 3
        };
    }

    @Override
    public boolean canBoosterApply(TileMachineBase tile, ItemStack booster) {
        return true;
    }

}
