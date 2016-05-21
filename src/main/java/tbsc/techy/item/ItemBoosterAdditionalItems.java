package tbsc.techy.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tbsc.techy.tile.TileMachineBase;

import java.util.List;

/**
 * Additional items booster
 *
 * Created by tbsc on 5/3/16.
 */
public class ItemBoosterAdditionalItems extends ItemBoosterBase {

    public ItemBoosterAdditionalItems() {
        super("itemBoosterBonus");

        setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(itemIn, 1, 1));
        subItems.add(new ItemStack(itemIn, 1, 2));
    }

    @Override
    public void initModel() {
        ModelBakery.registerItemVariants(this, new ResourceLocation(getRegistryName() + "T0"), new ResourceLocation(getRegistryName() + "T1"), new ResourceLocation(getRegistryName() + "T2"));
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName() + "T0", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName() + "T1", "inventory"));
        ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(getRegistryName() + "T2", "inventory"));
    }

    @Override
    public int getEnergyModifier(int tier) {
        switch (tier) {
            case 0:
                return 0; // +%
            case 1:
                return 50; // +%
            case 2:
                return 85; // +%
            default:
                return 0; // +%
        }
    }

    @Override
    public int getTimeModifier(int tier) {
        switch (tier) {
            case 0:
                return 0;
            case 1:
                return 50;
            case 2:
                return 85;
            default:
                return 0;
        }
    }

    @Override
    public int getExperienceModifier(int tier) {
        switch (tier) {
            case 0:
                return 0;
            case 1:
                return 25;
            case 2:
                return 55;
            default:
                return 0;
        }
    }

    @Override
    public int getAdditionalItemModifier(int tier) {
        switch (tier) {
            case 0:
                return -1;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public int[] getTiers() {
        return new int[] {
                0, 1, 2
        };
    }

    @Override
    public boolean canBoosterApply(TileMachineBase tile, ItemStack booster) {
        return true;
    }

}
