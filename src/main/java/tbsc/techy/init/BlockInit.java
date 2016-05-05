package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.machine.crusher.BlockCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;

/**
 * Loads and contains the blocks (+ TileEntities) of Techy,
 *
 * Created by tbsc on 3/26/16.
 */
public class BlockInit {

    // Instances of blocks
    public static BlockBaseFacingMachine blockPoweredFurnace;
    public static BlockBaseFacingMachine blockCrusher;

    /**
     * Gets called on preInit stage and loads all of the blocks and TileEntities.
     */
    public static void init() {
        blockPoweredFurnace = new BlockPoweredFurnace();
        blockCrusher = new BlockCrusher();

        GameRegistry.registerBlock(blockPoweredFurnace);
        GameRegistry.registerBlock(blockCrusher);

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
        GameRegistry.registerTileEntity(TileCrusher.class, "tileCrusher");
    }

    /**
     * Loads models of blocks.
     * Each block needs to be added manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPoweredFurnace.initModel();
        blockCrusher.initModel();
    }

}
