package tbsc.techy.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.Techy;
import tbsc.techy.machine.crusher.ContainerCrusher;
import tbsc.techy.machine.crusher.GuiCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.ContainerPoweredFurnace;
import tbsc.techy.machine.furnace.GuiPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;
import tbsc.techy.machine.generator.ContainerGeneratorBase;
import tbsc.techy.machine.generator.GuiGeneratorBase;
import tbsc.techy.machine.powercell.ContainerGenericPowerCell;
import tbsc.techy.machine.powercell.GuiGenericPowerCell;
import tbsc.techy.machine.powercell.TilePowerCellBase;
import tbsc.techy.tile.TileMachineBase;

/**
 * Gui handler for Techy's GUIs.
 * What it does is depending on the ID given, return the correct GUI to open.
 *
 * Created by tbsc on 3/29/16.
 */
public class TechyGuiHandler implements IGuiHandler {

    /**
     * Returns a container based on GUI id
     * @param ID ID for the GUI
     * @param player the player who opened the GUI
     * @param world the world it happened in
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @return Container relevant to the GUI
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new ContainerPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new ContainerCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.COAL_GENERATOR_GUI_ID) {
            return new ContainerGeneratorBase(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.POWER_CELL_GUI_ID) {
            TilePowerCellBase tile = (TilePowerCellBase) world.getTileEntity(new BlockPos(x, y, z));
            return new ContainerGenericPowerCell(player.inventory, tile, tile.inventorySize);
        }
        return null;
    }

    /**
     * Returns a GUI based on GUI id
     * @param ID ID for the GUI
     * @param player the player who opened the GUI
     * @param world the world it happened in
     * @param x x pos
     * @param y y pos
     * @param z z pos
     * @return GUI
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new GuiPoweredFurnace(new ContainerPoweredFurnace(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z))), new BlockPos(x, y, z), world);
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new GuiCrusher(new ContainerCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z))), new BlockPos(x, y, z), world);
        }
        if (ID == Techy.COAL_GENERATOR_GUI_ID) {
            return new GuiGeneratorBase(new ContainerGeneratorBase(player.inventory, (TileMachineBase) world.getTileEntity(new BlockPos(x, y, z))), new BlockPos(x, y, z), world);
        }
        if (ID == Techy.POWER_CELL_GUI_ID) {
            TilePowerCellBase tile = (TilePowerCellBase) world.getTileEntity(new BlockPos(x, y, z));
            return new GuiGenericPowerCell(new ContainerGenericPowerCell(player.inventory, tile, tile.inventorySize), tile.getPos(), world, tile.inventorySize, tile.getGUITexture());
        }
        return null;
    }

}
