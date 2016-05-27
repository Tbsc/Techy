package tbsc.techy.client.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.render.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tbsc.techy.client.gui.element.TabSides;
import tbsc.techy.container.ContainerBase;
import tbsc.techy.tile.TileBase;
import tbsc.techy.tile.TileMachineBase;

import java.util.List;

/**
 * Machine GUI base class.
 * Most of the rendering (such as rendering the GUI itself) is done by {@link GuiBase}.
 *
 * Created by tbsc on 3/28/16.
 */
public abstract class GuiMachineBase extends GuiBase {

    public int tileInvSize;
    protected ResourceLocation guiTexture;
    protected BlockPos machine;
    protected World world;

    protected GuiMachineBase(ContainerBase containerBase, BlockPos pos, World world, int tileInvSize, ResourceLocation guiTexture) {
        super(containerBase, guiTexture);
        this.machine = pos;
        this.world = world;
        this.tileInvSize = tileInvSize;
        this.guiTexture = guiTexture;
    }

    @Override
    public void initGui() {
        super.initGui();
        addTab(new TabSides(this, xSize + 1, 0, 22 + 28, 22 + 28, (TileBase) world.getTileEntity(machine)));
    }

    int energyBarStartX = xSize - 24;
    int energyBarStartY = 22;
    int energyBarWidth = 16;
    int energyBarHeight = 42;

    /**
     * Renders an energy bar on the background layer
     * @param partialTick
     * @param x
     * @param y
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int x, int y) {
        super.drawGuiContainerBackgroundLayer(partialTick, x, y);
        TileMachineBase tile = (TileMachineBase) world.getTileEntity(machine);
        if (tile != null) {
            int percentage = tile.getField(0) * 42 / tile.getField(4);
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;

            RenderHelper.bindTexture(ElementEnergyStored.DEFAULT_TEXTURE);
            drawSizedTexturedModalRect(i + energyBarStartX, j + energyBarStartY, 0, 0, 16, 42, 32, 64);
            drawSizedTexturedModalRect(i + energyBarStartX, j + energyBarStartY + energyBarHeight - percentage, energyBarWidth, energyBarHeight - percentage, 16, percentage, 32, 64);
        }
    }

    @Override
    public void addTooltips(List<String> tooltip) {
        super.addTooltips(tooltip);
        TileMachineBase tile = (TileMachineBase) world.getTileEntity(machine);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (tile != null) {
            if (mouseX >= energyBarStartX && mouseX <= energyBarStartX + energyBarWidth && mouseY >= energyBarStartY && mouseY <= energyBarStartY + energyBarHeight) { // Mouse is currently on the energy bar
                if (tile.getField(4) < 0) {
                    tooltip.add("Infinite RF");
                } else {
                    tooltip.add(tile.getField(0) + " / " + tile.getField(4) + " RF");
                }
                tooltip.add(tile.getField(3) + " RF/t");
            }
        }
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
        TileBase tile = (TileBase) world.getTileEntity(machine);
        if (tile != null) {
            fontRendererObj.drawString(tile.getName(), 8, 6, 0x404040);
        }
    }

}
