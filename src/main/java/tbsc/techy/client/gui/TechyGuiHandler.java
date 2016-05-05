package tbsc.techy.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.Techy;
import tbsc.techy.machine.crusher.ContainerCrusher;
import tbsc.techy.machine.crusher.GuiCrusher;
import tbsc.techy.machine.crusher.TileCrusher;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.ContainerPoweredFurnace;
import tbsc.techy.machine.furnace.GuiPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;

/**
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
            return new ContainerPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)), BlockPoweredFurnace.tileInvSize);
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new ContainerCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z)));
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
            return new GuiPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == Techy.CRUSHER_GUI_ID) {
            return new GuiCrusher(player.inventory, (TileCrusher) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

}
