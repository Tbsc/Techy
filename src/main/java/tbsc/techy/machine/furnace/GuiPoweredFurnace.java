package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;

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

    /**
     * Unused right now (TODO Make it used)
     * @param xGuiStart Pixel on screen on which GUI starts rendering on the x axis
     * @param yGuiStart same as {@code xGuiStart}, just on the y axis
     */
    @Override
    protected void renderProgressBar(int xGuiStart, int yGuiStart) {

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