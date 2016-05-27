package tbsc.techy.block;

import net.minecraft.block.material.Material;
import tbsc.techy.init.BlockInit;

/**
 * Adds all ores to the game
 *
 * Created by tbsc on 5/26/16.
 */
public class BlockOreBase extends BlockBase {

    public BlockOreBase(String unlocalizedName) {
        super(Material.ROCK, unlocalizedName);
    }

    public enum OreType {
        COPPER("blockOreCopper", "Copper", BlockInit.blockOreCopper),
        TIN("blockOreTin", "Tin", BlockInit.blockOreTin),
        SILVER("blockOreSilver", "Silver", BlockInit.blockOreSilver),
        ALUMINUM("blockOreAluminium", "Aluminum", BlockInit.blockOreAluminium),
        LITHIUM("blockOreLithium", "Lithium", BlockInit.blockOreLithium);

        public BlockOreBase ore;
        public String name;
        public String regName;

        OreType(String unlocalizedName, String registryName, BlockOreBase ore) {
            this.ore = ore;
            this.name = unlocalizedName;
            this.regName = registryName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
