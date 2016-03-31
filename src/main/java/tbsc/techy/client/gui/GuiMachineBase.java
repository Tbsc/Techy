package tbsc.techy.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tbsc.techy.container.ContainerBase;

/**
 * Machine GUI base class.
 *
 * Created by tbsc on 3/28/16.
 */
public abstract class GuiMachineBase extends GuiContainer {

    public int tileInvSize;
    protected ResourceLocation guiTexture;
    protected ContainerBase container;

    public GuiMachineBase(ContainerBase containerBase, int tileInvSize, ResourceLocation guiTexture) {
        super(containerBase);
        this.container = containerBase;
        this.tileInvSize = tileInvSize;
        this.guiTexture = guiTexture;
    }

    protected void renderMouseTooltip(String msg, int x, int y) {
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
     *
     * @param xGuiStart Pixel on screen on which GUI starts rendering on the x axis
     * @param yGuiStart same as {@code xGuiStart}, just on the y axis
     */
    protected abstract void renderProgressBar(int xGuiStart, int yGuiStart);

    protected int getStringWidth(String text) {
        return fontRendererObj.getStringWidth(text);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        fontRendererObj.drawString("Inventory", 8, this.ySize - 94, 404040);
        fontRendererObj.drawString(container.tileBase.getName(), 6, 6, 404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

}
