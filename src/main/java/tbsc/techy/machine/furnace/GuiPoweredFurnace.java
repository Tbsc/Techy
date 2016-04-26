package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;

/**
 * GUI for the powered furnace.
 * Right now does nothing other than define the location of the GUI texture.
 *
 * Created by tbsc on 3/28/16.
 */
public class GuiPoweredFurnace extends GuiMachineBase {

    public GuiPoweredFurnace(IInventory playerInv, TilePoweredFurnace tile) {
        super(new ContainerPoweredFurnace(playerInv, tile, BlockPoweredFurnace.tileInvSize), BlockPoweredFurnace.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiPoweredFurnace.png"));
    }

    @Override
    public void drawScreen(int x, int y, float partialTick) {
        super.drawScreen(x, y, partialTick);
        addElement(new ElementProgressBar(this, 80, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, ((TilePoweredFurnace) container.tileBase).progress, 17, ((TilePoweredFurnace) container.tileBase).totalProgress));
    }

    /**
     * Draws the background layer of the GUI.
     * All done by the supermethod.
     * @param partialTicks
     * @param mouseX Position of the mouse pointer on the x-axis.
     * @param mouseY Position of the mouse pointer on the y-axis.
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    }

}