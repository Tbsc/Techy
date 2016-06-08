package tbsc.techy.machine.powercell.creative;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.machine.powercell.GuiGenericPowerCell;

/**
 * Re-implementation of the generic power cell gui for the creative power cell, as it
 * has a few differences, such as position of energy bar and only 1 slot - output slot.
 *
 * Created by tbsc on 6/6/16.
 */
public class GuiCreativePowerCell extends GuiGenericPowerCell {

    public GuiCreativePowerCell(ContainerBase containerBase, BlockPos pos, World world, int tileInvSize, ResourceLocation guiTexture) {
        super(containerBase, pos, world, tileInvSize, guiTexture);

        energyBarStartX = 108;
        energyBarStartY = 20;
    }

}
