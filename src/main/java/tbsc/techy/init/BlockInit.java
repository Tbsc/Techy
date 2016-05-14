package tbsc.techy.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.BlockBase;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.block.BlockMachineBase;
import tbsc.techy.block.pipe.BlockPipeEnergy;
import tbsc.techy.machine.crusher.BlockCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.tile.pipe.TilePipeEnergy;

/**
 * Loads and contains the blocks (+ TileEntities) of Techy,
 *
 * Created by tbsc on 3/26/16.
 */
public class BlockInit {

    // Instances of blocks
    public static BlockBaseFacingMachine blockPoweredFurnace;
    public static BlockBaseFacingMachine blockCrusher;
    public static BlockMachineBase blockMachineBaseBasic;
    public static BlockMachineBase blockMachineBaseImproved;
    public static BlockMachineBase blockMachineBaseAdvanced;
    public static BlockPipeEnergy blockPipeEnergy;
    public static BlockBase blockPixelTest;

    /**
     * Gets called on preInit stage and loads all of the blocks and TileEntities.
     */
    public static void init() {
        blockPoweredFurnace = new BlockPoweredFurnace();
        blockCrusher = new BlockCrusher();
        blockMachineBaseBasic = new BlockMachineBase("blockMachineBaseBasic");
        blockMachineBaseImproved = new BlockMachineBase("blockMachineBaseImproved");
        blockMachineBaseAdvanced = new BlockMachineBase("blockMachineBaseAdvanced");
        blockPipeEnergy = new BlockPipeEnergy();
        blockPixelTest = new BlockMachineBase("blockPixelTest");

        GameRegistry.registerBlock(blockPoweredFurnace);
        GameRegistry.registerBlock(blockCrusher);
        GameRegistry.registerBlock(blockMachineBaseBasic);
        GameRegistry.registerBlock(blockMachineBaseImproved);
        GameRegistry.registerBlock(blockMachineBaseAdvanced);
        GameRegistry.registerBlock(blockPipeEnergy);
        GameRegistry.registerBlock(blockPixelTest);

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
        GameRegistry.registerTileEntity(TileCrusher.class, "tileCrusher");
        GameRegistry.registerTileEntity(TilePipeEnergy.class, "pipeEnergy");
    }

    /**
     * Loads models of blocks.
     * Each block needs to be added manually.
     */
    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockPoweredFurnace.initModel();
        blockCrusher.initModel();
        blockMachineBaseBasic.initModel();
        blockMachineBaseImproved.initModel();
        blockMachineBaseAdvanced.initModel();
        blockPipeEnergy.initModel();
        blockPixelTest.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        blockPipeEnergy.initItemModel();
    }

}
