package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * Progress bar element. Based on values, will render a progress bar on screen and
 * update it by the progress.
 * Can select type of progress bar and how to render it.
 *
 * Created by tbsc on 4/26/16.
 */
public class ElementProgressBar extends ElementBase {

    private Minecraft mc;
    public ResourceLocation barLocation;
    public int notWorkingStartX, workingStartX, barWidth,
               notWorkingStartY, workingStartY, barHeight, totalProgress;
    public boolean isHorizontal;
    public ElementProgressBar(GuiBase gui, int posX, int posY, int width, int height, ResourceLocation barLocation,
                              int notWorkingStartX, int notWorkingStartY, int workingStartX,
                              int workingStartY, boolean isHorizontal, int currentWidth, int currentHeight, int totalProgress) {
        super(gui, posX, posY, width, height);
        mc = Minecraft.getMinecraft();
        this.barLocation = barLocation;
        this.notWorkingStartX = notWorkingStartX;
        this.notWorkingStartY = notWorkingStartY;
        this.workingStartX = workingStartX;
        this.workingStartY = workingStartY;
        this.barWidth = currentWidth;
        this.barHeight = currentHeight;
        this.totalProgress = totalProgress;
        this.isHorizontal = isHorizontal;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        mc.getTextureManager().bindTexture(barLocation);
        drawTexturedModalRect(posX, posY, notWorkingStartX, notWorkingStartY, sizeX, sizeY); // sizeX - width & sizeY - height from constructor
        if (isHorizontal) {
            if (barWidth > 0) { // Prevents dividing by zero
                if (totalProgress != 0) {
                    int percentage = barWidth * sizeX / totalProgress;
                    drawTexturedModalRect(posX, posY, workingStartX, workingStartY, percentage, barHeight);
                }
            }
        } else {
            if (barHeight > 0) { // Prevents dividing by zero
                if (totalProgress != 0) {
                    int percentage = barHeight * sizeY / totalProgress;
                    drawTexturedModalRect(posX, posY + sizeY - percentage, workingStartX, sizeX - workingStartY, barWidth, percentage);
                }
            }
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

}
