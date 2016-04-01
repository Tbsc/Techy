package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;

/**
 * Created by tbsc on 3/26/16.
 */
public class BlockInit {

    public static BlockBaseFacingMachine blockPoweredFurnace;

    public static void init() {
        blockPoweredFurnace = new BlockPoweredFurnace();

        GameRegistry.registerBlock(blockPoweredFurnace);

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPoweredFurnace.initModel();
    }

}
