package tbsc.techy.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.Techy;
import tbsc.techy.machine.furnace.BlockPoweredFurnace;
import tbsc.techy.machine.furnace.ContainerPoweredFurnace;
import tbsc.techy.machine.furnace.GuiPoweredFurnace;
import tbsc.techy.machine.furnace.TilePoweredFurnace;

/**
 * Created by tbsc on 3/29/16.
 */
public class TechyGuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new ContainerPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)), BlockPoweredFurnace.tileInvSize);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Techy.POWERED_FURNACE_GUI_ID) {
            return new GuiPoweredFurnace(player.inventory, (TilePoweredFurnace) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

}
