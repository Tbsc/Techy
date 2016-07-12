package tbsc.techy.client.gui.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tbsc.techy.api.capability.gui.IHasGUI;

import java.util.HashMap;

/**
 * Techy's GUI handler. Using a registry system for registering GUIs to the handler,
 * it returns a different GUI/container from those that have been registered to the game,
 * based on the ID they have been registered with.
 *
 * Created by tbsc on 11/07/2016.
 */
public class TechyGUIHandler implements IGuiHandler {

    /**
     * Registry map. Contains information about Techy's GUIs, like what GUI will be shown
     * when opening some ID.
     */
    private static HashMap<Integer, IHasGUI> registryMap = new HashMap<>();

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return registryMap.get(ID).getContainer(player, world, new BlockPos(x, y, z));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return registryMap.get(ID).getGUI(player, world, new BlockPos(x, y, z));
    }

    public static void registerGUI(IHasGUI cap) {
        registryMap.put(cap.getGUIID(), cap);
    }

}
