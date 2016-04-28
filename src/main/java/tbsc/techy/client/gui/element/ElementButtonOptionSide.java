package tbsc.techy.client.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButtonManaged;
import cofh.lib.render.RenderHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import tbsc.techy.api.SideConfiguration;
import tbsc.techy.api.Sides;
import tbsc.techy.tile.TileBase;

import java.util.List;

/**
 * Value 0 - Disabled
 * Value 1 - Input
 * Value 2 - Output
 * Value 3 - IO
 *
 * Created by tbsc on 4/27/16.
 */
public class ElementButtonOptionSide extends ElementButtonManaged {

    private TileBase tile;
    private Sides side;
    private SideConfiguration currentConfig;

    public ElementButtonOptionSide(GuiBase containerScreen, int x, int y, int width, int height, TileBase tile, Sides side, SideConfiguration currentConfig) {
        super(containerScreen, x, y, width, height, "");
        FMLLog.info("Side option button added! Side: " + side.name() + ", Config: " + currentConfig.toString());
        this.tile = tile;
        this.side = side;
        this.currentConfig = currentConfig;
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {
        RenderHelper.bindTexture(new ResourceLocation("Techy:textures/gui/element/sideConfigRender.png"));
        switch (currentConfig) {
            case DISABLED:
                drawTexturedModalRect(posX, posY, 30, 0, sizeX, sizeY);
                break;
            case INPUT:
                drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
                break;
            case OUTPUT:
                drawTexturedModalRect(posX, posY, 20, 0, sizeX, sizeY);
                break;
            case IO:
                drawTexturedModalRect(posX, posY, 10, 0, sizeX, sizeY);
                break;
            default:
                drawTexturedModalRect(posX, posY, 40, 0, sizeX, sizeY);
                break;
        }
    }

    @Override
    public void addTooltip(List<String> list) {
        list.add(currentConfig.toString());
    }

    @Override
    public void onClick() {
        currentConfig = currentConfig.cycleForward();
        onValueChanged(currentConfig);
    }

    @Override
    public void onRightClick() {
        currentConfig = currentConfig.cycleBackward();
        onValueChanged(currentConfig);
    }

    public void onValueChanged(SideConfiguration changedTo) {
        tile.setConfigurationForSide(side, changedTo);
    }

}
