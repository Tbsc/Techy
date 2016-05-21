package tbsc.techy.init;

import net.minecraft.item.ItemBlock;
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
import tbsc.techy.machine.generator.TileGeneratorBase;
import tbsc.techy.machine.generator.coal.BlockCoalGenerator;
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
    public static BlockBaseFacingMachine blockCoalGenerator;

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
        blockCoalGenerator = new BlockCoalGenerator();

        GameRegistry.register(blockPoweredFurnace);
        GameRegistry.register(new ItemBlock(blockPoweredFurnace), blockPoweredFurnace.getRegistryName());
        GameRegistry.register(blockCrusher);
        GameRegistry.register(new ItemBlock(blockCrusher), blockCrusher.getRegistryName());
        GameRegistry.register(blockMachineBaseBasic);
        GameRegistry.register(new ItemBlock(blockMachineBaseBasic), blockMachineBaseBasic.getRegistryName());
        GameRegistry.register(blockMachineBaseImproved);
        GameRegistry.register(new ItemBlock(blockMachineBaseImproved), blockMachineBaseImproved.getRegistryName());
        GameRegistry.register(blockMachineBaseAdvanced);
        GameRegistry.register(new ItemBlock(blockMachineBaseAdvanced), blockMachineBaseAdvanced.getRegistryName());
        GameRegistry.register(blockPipeEnergy);
        GameRegistry.register(new ItemBlock(blockPipeEnergy), blockPipeEnergy.getRegistryName());
        GameRegistry.register(blockPixelTest);
        GameRegistry.register(new ItemBlock(blockPixelTest), blockPixelTest.getRegistryName());
        GameRegistry.register(blockCoalGenerator);
        GameRegistry.register(new ItemBlock(blockCoalGenerator), blockCoalGenerator.getRegistryName());

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
        GameRegistry.registerTileEntity(TileCrusher.class, "tileCrusher");
        GameRegistry.registerTileEntity(TilePipeEnergy.class, "pipeEnergy");
        GameRegistry.registerTileEntity(TileGeneratorBase.class, "tileCoalGenerator");
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
        blockCoalGenerator.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        blockPipeEnergy.initItemModel();
    }

}
