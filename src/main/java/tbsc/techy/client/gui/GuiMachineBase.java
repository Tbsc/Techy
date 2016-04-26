package tbsc.techy.client.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileMachineBase;

/**
 * Machine GUI base class.
 * Most of the rendering (such as rendering the GUI itself) is done by {@link GuiBase}.
 *
 * Created by tbsc on 3/28/16.
 */
public abstract class GuiMachineBase extends GuiBase {

    public int tileInvSize;
    protected ResourceLocation guiTexture;
    protected ContainerBase container;

    public GuiMachineBase(ContainerBase containerBase, int tileInvSize, ResourceLocation guiTexture) {
        super(containerBase, guiTexture);
        this.container = containerBase;
        this.tileInvSize = tileInvSize;
        this.guiTexture = guiTexture;
    }

    @Override
    public void initGui() {
        super.initGui();
        addElement(new ElementEnergyStored(this, xSize - 24, 12, ((TileMachineBase) container.tileBase).energyStorage));
    }

    ///////////
    // Utils //
    ///////////

    /**
     * Renders a vanilla looking tooltip based on the values given.
     * Unlike {@code renderToolTip}, this draws text while renderToolTip draws {@link net.minecraft.item.ItemStack}s.
     * @param msg The text to be displayed inside the tooltip.
     * @param x Position of the tooltip on the x-axis, can be mouseX
     * @param y Position of the tooltip on the y-axis, can be mouseY
     */
    protected void renderTextTooltip(String msg, int x, int y) {
        if(msg != null) {
            int index = 0;
            int width = 0;
            String[] rx = msg.split("\n");
            int ry = rx.length;

            int the_width;
            for(int var8 = 0; var8 < ry; ++var8) {
                String s = rx[var8];
                the_width = this.getStringWidth(s);
                if(the_width > width) {
                    width = the_width;
                }
            }

            int var12 = x - width - 5;
            ry = y;
            if(var12 < 0) {
                var12 = x + 12;
            }

            String[] var13 = msg.split("\n");
            int var14 = var13.length;

            for(the_width = 0; the_width < var14; ++the_width) {
                String s1 = var13[the_width];
                drawGradientRect(var12 - 3, ry - (index == 0?3:0) + index, var12 + width + 3, ry + 8 + 3 + index, -1073741824, -1073741824);
                fontRendererObj.drawStringWithShadow(s1, var12, ry + index, 16777215);
                index += 10;
            }

        }
    }

    /**
     * @param text Text to be checked
     * @return The width of the text in pixels
     */
    protected int getStringWidth(String text) {
        return fontRendererObj.getStringWidth(text);
    }

    /**
     * Renders basic GUI stuff, such as machine name.
     * @param mouseX Position of the mouse pointer on the x-axis
     * @param mouseY Position of the mouse pointer on the y-axis
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        fontRendererObj.drawString(container.tileBase.getName(), 8, 6, 0x404040);
    }

}
