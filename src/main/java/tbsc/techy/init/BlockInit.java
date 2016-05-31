package tbsc.techy.init;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tbsc.techy.block.BlockBaseFacingMachine;
import tbsc.techy.block.BlockMachineBase;
import tbsc.techy.block.BlockOreBase;
import tbsc.techy.block.pipe.BlockPipeEnergy;
import tbsc.techy.machine.crusher.BlockCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.machine.generator.coal.BlockCoalGenerator;
import tbsc.techy.machine.generator.coal.TileCoalGenerator;
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
    public static BlockBaseFacingMachine blockCoalGenerator;
    public static BlockOreBase blockOreCopper;
    public static BlockOreBase blockOreTin;
    public static BlockOreBase blockOreSilver;
    public static BlockOreBase blockOreAluminium;
    public static BlockOreBase blockOreLithium;

    /**
     * Gets called on preInit stage and loads all of the blocks and TileEntities.
     */
    public static void init() {
        blockPoweredFurnace = new BlockPoweredFurnace();
        blockCrusher = new BlockCrusher();
        blockMachineBaseBasic = new BlockMachineBase("blockMachineBaseBasic");
        blockMachineBaseImproved = new BlockMachineBase("blockMachineBaseImproved");
        blockMachineBaseAdvanced = new BlockMachineBase("blockMachineBaseAdvanced");
        blockPipeEnergy = new BlockPipeEnergy(160 * 6, 160); // Can transfer 2 coal generators, and can contain enough for 2 from all sides
        blockCoalGenerator = new BlockCoalGenerator();
        blockOreCopper = new BlockOreBase("blockOreCopper");
        blockOreTin = new BlockOreBase("blockOreTin");
        blockOreSilver = new BlockOreBase("blockOreSilver");
        blockOreAluminium = new BlockOreBase("blockOreAluminium");
        blockOreLithium = new BlockOreBase("blockOreLithium");

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
        GameRegistry.register(blockCoalGenerator);
        GameRegistry.register(new ItemBlock(blockCoalGenerator), blockCoalGenerator.getRegistryName());
        GameRegistry.register(blockOreCopper);
        GameRegistry.register(new ItemBlock(blockOreCopper), blockOreCopper.getRegistryName());
        GameRegistry.register(blockOreTin);
        GameRegistry.register(new ItemBlock(blockOreTin), blockOreTin.getRegistryName());
        GameRegistry.register(blockOreSilver);
        GameRegistry.register(new ItemBlock(blockOreSilver), blockOreSilver.getRegistryName());
        GameRegistry.register(blockOreAluminium);
        GameRegistry.register(new ItemBlock(blockOreAluminium), blockOreAluminium.getRegistryName());
        GameRegistry.register(blockOreLithium);
        GameRegistry.register(new ItemBlock(blockOreLithium), blockOreLithium.getRegistryName());

        GameRegistry.registerTileEntity(TilePoweredFurnace.class, "tilePoweredFurnace");
        GameRegistry.registerTileEntity(TileCrusher.class, "tileCrusher");
        GameRegistry.registerTileEntity(TilePipeEnergy.class, "pipeEnergy");
        GameRegistry.registerTileEntity(TileCoalGenerator.class, "tileCoalGenerator");
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
        blockCoalGenerator.initModel();
        blockOreCopper.initModel();
        blockOreTin.initModel();
        blockOreSilver.initModel();
        blockOreAluminium.initModel();
        blockOreLithium.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        blockPipeEnergy.initItemModel();
    }

}
