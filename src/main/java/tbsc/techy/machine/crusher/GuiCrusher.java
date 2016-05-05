package tbsc.techy.machine.crusher;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.client.gui.GuiMachineBase;
import tbsc.techy.client.gui.element.ElementProgressBar;
import tbsc.techy.client.gui.element.ElementSlotRender;
import tbsc.techy.tile.TileMachineBase;

/**
 * GUI for the crusher
 *
 * Created by tbsc on 5/4/16.
 */
public class GuiCrusher extends GuiMachineBase {

    public GuiCrusher(IInventory playerInv, TileMachineBase tileBase) {
        super(new ContainerCrusher(playerInv, tileBase), BlockCrusher.tileInvSize, new ResourceLocation("Techy:textures/gui/container/guiCrusher.png"));
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
        addElement(new ElementProgressBar(this, 72, 37, 22, 16, new ResourceLocation("Techy:textures/gui/element/furnaceProgressBar.png"), 0, 0, 22, 0, container.tileBase.progress, 17, container.tileBase.totalProgress));
    }

}
