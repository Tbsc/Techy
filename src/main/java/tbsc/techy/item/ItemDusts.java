package tbsc.techy.item;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Generic class for all dusts.
 *
 * Created by tbsc on 5/5/16.
 */
public class ItemDusts extends ItemBase {

    public ItemDusts() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(64);
        setRegistryName("itemDust");

        for (DustType dustType : DustType.values()) {
            OreDictionary.registerOre("dust" + dustType.regName, new ItemStack(this, 1, dustType.id));
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (DustType dustType : DustType.values()) {
            subItems.add(new ItemStack(itemIn, 1, dustType.id));
        }
    }

    @Override
    public void initModel() {
        ResourceLocation[] textures = new ResourceLocation[] {};
        for (DustType type : DustType.values()) {
            if (type != DustType.IRON)
            ArrayUtils.add(textures, new ResourceLocation(getRegistryName() + type.regName));
        }
        ModelBakery.registerItemVariants(this, textures);
        for (DustType type : DustType.values()) {
            ModelLoader.setCustomModelResourceLocation(this, type.id, new ModelResourceLocation(getRegistryName() + type.regName, "inventory"));
            FMLLog.info(getRegistryName());
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.Techy:" + DustType.valueOf(stack.getMetadata()).toString();
    }

    public enum DustType {

        IRON("itemDustIron", "Iron", 0),
        GOLD("itemDustGold", "Gold", 1),
        DIAMOND("itemDustDiamond", "Diamond", 2),
        COAL("itemDustCoal", "Coal", 3),
        EMERALD("itemDustEmerald", "Emerald", 4),
        WOOD("itemDustWood", "Wood", 5),
        STONE("itemDustStone", "Stone", 6);

        public int id;
        public String name;
        public String regName;

        DustType(String unlocalizedName, String registryName, int id) {
            this.id = id;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        public static DustType valueOf(int id) {
            for (DustType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
