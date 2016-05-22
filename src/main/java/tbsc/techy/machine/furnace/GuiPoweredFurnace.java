package tbsc.techy.machine.furnace;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;

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
        addElement(new ElementProgressBar(this, 80, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, true, ((TilePoweredFurnace) container.tileBase).progress, 17, ((TilePoweredFurnace) container.tileBase).totalProgress));
    }

}