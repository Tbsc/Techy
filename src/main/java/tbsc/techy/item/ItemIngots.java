package tbsc.techy.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * Generic class for adding ingots to the game.
 * Any ingots that are added need to register an instance of this class DIRECTLY.
 *
 * Created by tbsc on 5/26/16.
 */
public class ItemIngots extends ItemBase {

    public ItemIngots() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(64);
        setRegistryName("itemIngot");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (IngotType ingotTy : IngotType.values()) {
            subItems.add(new ItemStack(itemIn, 1, ingotTy.id));
        }
    }

    @Override
    public void initModel() {
        ResourceLocation[] textures = new ResourceLocation[] {};
        for (IngotType type : IngotType.values()) {
            if (type != IngotType.COPPER)
                ArrayUtils.add(textures, new ResourceLocation(getRegistryName() + type.regName));
        }
        ModelBakery.registerItemVariants(this, textures);
        for (IngotType type : IngotType.values()) {
            ModelLoader.setCustomModelResourceLocation(this, type.id, new ModelResourceLocation(getRegistryName() + type.regName, "inventory"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item.Techy:" + IngotType.valueOf(stack.getMetadata()).toString();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if (stack.getMetadata() == IngotType.ALUMINIUM.id) {
            tooltip.add("Yes, ALUMINIUM. I don't care what you want, I call it aluminium.");
        }
    }

    public enum IngotType {
        COPPER("itemIngotCopper", "Copper", 0),
        TIN("itemIngotTin", "Tin", 1),
        SILVER("itemIngotSilver", "Silver", 2),
        ALUMINIUM("itemIngotAluminium", "Aluminium", 3),
        LITHIUM("itemIngotLithium", "Lithium", 4),
        BRONZE("itemIngotBronze", "Bronze", 5);

        public int id;
        public String name;
        public String regName;

        IngotType(String unlocalizedName, String registryName, int id) {
            this.id = id;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        public static IngotType valueOf(int id) {
            for (IngotType type : values()) {
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
