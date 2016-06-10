package tbsc.techy.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import tbsc.techy.init.BlockInit;
import tbsc.techy.init.ItemInit;
import tbsc.techy.item.ItemIngots;

/**
 * Adds all ores to the game
 *
 * Created by tbsc on 5/26/16.
 */
public class BlockOreBase extends BlockBase {

    public BlockOreBase(String unlocalizedName) {
        super(Material.ROCK, unlocalizedName);
        setHardness(3.0F);
        setResistance(5.0F);
    }

    public enum OreType {
        COPPER("blockOreCopper", "Copper", BlockInit.blockOreCopper, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.COPPER.id)),
        TIN("blockOreTin", "Tin", BlockInit.blockOreTin, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.TIN.id)),
        SILVER("blockOreSilver", "Silver", BlockInit.blockOreSilver, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.SILVER.id)),
        ALUMINIUM("blockOreAluminium", "Aluminium", BlockInit.blockOreAluminium, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.ALUMINIUM.id)),
        LITHIUM("blockOreLithium", "Lithium", BlockInit.blockOreLithium, new ItemStack(ItemInit.itemIngots, 1, ItemIngots.IngotType.LITHIUM.id));

        public BlockOreBase ore;
        public ItemStack ingot;
        public String name;
        public String regName;

        OreType(String unlocalizedName, String registryName, BlockOreBase ore, ItemStack ingot) {
            this.ore = ore;
            this.ingot = ingot;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
