package tbsc.techy.machine.powercell;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.container.ContainerBase;

/**
 * Generic GUI for power cells
 *
 * Created by tbsc on 6/3/16.
 */
public class GuiGenericPowerCell extends GuiMachineBase {

    public GuiGenericPowerCell(ContainerBase containerBase, BlockPos pos, World world, int tileInvSize, ResourceLocation guiTexture) {
        super(containerBase, pos, world, tileInvSize, guiTexture);
        energyBarStartX = 81;
        energyBarStartY = 20;
    }

}
