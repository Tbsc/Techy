package tbsc.techy.machine.furnace;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;
import tbsc.techy.tile.TileMachineBase;

/**
 * GUI for the powered furnace.
 * Right now does nothing other than define the location of the GUI texture.
 *
 * Created by tbsc on 3/28/16.
 */
public class GuiPoweredFurnace extends GuiMachineBase {

    public GuiPoweredFurnace(ContainerPoweredFurnace container, BlockPos machine, World world) {
        super(container, machine, world, BlockPoweredFurnace.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiPoweredFurnace.png"));
    }

    @Override
    public void initGui() {
        super.initGui();
        addElement(new ElementSlotRender(this, -21, 15));
        addElement(new ElementSlotRender(this, -21, 35));
        addElement(new ElementSlotRender(this, -21, 55));
        addElement(new ElementSlotRender(this, -21, 75));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
        TileMachineBase tile = (TileMachineBase) world.getTileEntity(machine);
        if (tile != null) {
            addElement(new ElementProgressBar(this, 80, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, tile.getField(1), 16, tile.getField(2)));
        }
    }

}