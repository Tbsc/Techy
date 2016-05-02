package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import cofh.lib.render.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;

/**
 * An element that will just render a slot background where a slot is.
 * Size is always 18x18 and texture is hard coded.
 *
 * Created by tbsc on 5/2/16.
 */
public class ElementSlotRender extends ElementBase {

    float redTint, greenTint, blueTint;

    public ElementSlotRender(GuiBase gui, int posX, int posY, float redTint, float greenTint, float blueTint) {
        super(gui, posX, posY);
        setSize(18, 18);
        setTexture("Techy:textures/gui/element/slot.png", 18, 18);
        this.redTint = redTint;
        this.greenTint = greenTint;
        this.blueTint = blueTint;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        RenderHelper.bindTexture(texture);
        GlStateManager.color(redTint, greenTint, blueTint);
        GlStateManager.disableAlpha();
        drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        GlStateManager.enableAlpha();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

}
