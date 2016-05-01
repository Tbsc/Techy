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
    public ElementProgressBar(GuiBase gui, int posX, int posY, int width, int height, ResourceLocation barLocation,
                              int notWorkingStartX, int notWorkingStartY, int workingStartX,
                              int workingStartY, int barWidth, int barHeight, int totalProgress) {
        super(gui, posX, posY, width, height);
        mc = Minecraft.getMinecraft();
        this.barLocation = barLocation;
        this.notWorkingStartX = notWorkingStartX;
        this.notWorkingStartY = notWorkingStartY;
        this.workingStartX = workingStartX;
        this.workingStartY = workingStartY;
        this.barWidth = barWidth;
        this.barHeight = barHeight;
        this.totalProgress = totalProgress;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {
        mc.getTextureManager().bindTexture(barLocation);
        drawTexturedModalRect(posX, posY, notWorkingStartX, notWorkingStartY, sizeX, sizeY); // sizeX - width & sizeY - height from constructor
        if (barWidth > 0) { // Prevents dividing by zero
            if (totalProgress != 0) {
                int percentage = barWidth * sizeX / totalProgress;
                drawTexturedModalRect(posX, posY, workingStartX, workingStartY, percentage, barHeight);
            }
        }
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

}
